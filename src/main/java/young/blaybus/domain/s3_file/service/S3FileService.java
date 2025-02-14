package young.blaybus.domain.s3_file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import young.blaybus.domain.s3_file.controller.response.UploadS3FileResponse;

@Service
@RequiredArgsConstructor
public class S3FileService {

  private final CreateS3FileService createS3FileService;

  /**
   * S3 파일 업로드
   */
  public UploadS3FileResponse uploadS3File(MultipartFile file) {
    return createS3FileService.uploadS3File(file);
  }

  /**
   * S3 파일 삭제
   */
  public void deleteS3File(String fileUrl) {
    createS3FileService.deleteS3File(fileUrl);
  }

}
