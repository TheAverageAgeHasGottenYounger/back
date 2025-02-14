package young.blaybus.domain.s3_file.service;

import static com.amazonaws.services.s3.model.CannedAccessControlList.PublicRead;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import young.blaybus.api_response.exception.GeneralException;
import young.blaybus.api_response.status.ErrorStatus;
import young.blaybus.domain.s3_file.S3File;
import young.blaybus.domain.s3_file.controller.response.UploadS3FileResponse;
import young.blaybus.domain.s3_file.repository.S3FileRepository;
import young.blaybus.util.FileUtils;
import young.blaybus.util.RandomUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CreateS3FileService {

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;
  private final AmazonS3 amazonS3;
  private final AmazonS3Client amazonS3Client;
  private final S3FileRepository s3FileRepository;


  /**
   * S3 파일 업로드
   */
  public UploadS3FileResponse uploadS3File(MultipartFile file) {

    String fileName = generateRandomFileName(file.getOriginalFilename());

    try (InputStream inputStream = file.getInputStream()) {
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentLength(file.getSize());
      objectMetadata.setContentType(file.getContentType());
      PutObjectRequest request = new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
        .withCannedAcl(PublicRead);
      amazonS3.putObject(request);
    } catch (IOException e) {
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "파일 업로드에 실패했습니다.");
    }

    String fileUrl = amazonS3Client.getUrl(bucket, fileName).toString();

    S3File savedFile = s3FileRepository.save(
      S3File.builder()
        .originalName(file.getOriginalFilename())
        .fileName(fileName)
        .fileUrl(fileUrl)
        .fileSize(file.getSize())
        .build()
    );

    return UploadS3FileResponse.builder()
      .originalName(savedFile.getOriginalName())
      .fileUrl(savedFile.getFileUrl())
      .build();
  }

  /**
   * 중복 방지를 위한 파일명 난수화
   */
  private String generateRandomFileName(String originalName) {
    String fileName;
    do {
      fileName = RandomUtils.getRandomString().concat(".").concat(FileUtils.getFileExtension(originalName));
    } while (s3FileRepository.existsByFileName(fileName));

    return fileName;
  }

  /**
   * S3 파일 삭제
   */
  public void deleteS3File(String fileUrl) {
    String fileName = extractFileNameFromUrl(fileUrl);
    amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    s3FileRepository.deleteByFileName(fileName);
  }

  /**
   * 파일 URL에서 파일 이름을 추출
   */
  private String extractFileNameFromUrl(String fileUrl) {
    int fileIndex = fileUrl.lastIndexOf('/');
    return fileUrl.substring(fileIndex + 1);
  }
}
