package young.blaybus.domain.s3_file.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.s3_file.controller.response.UploadS3FileResponse;
import young.blaybus.domain.s3_file.service.S3FileService;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
@Tag(name = "S3 관련 API")
public class S3FileController {

  private final S3FileService s3FileService;


  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @Operation(summary = "S3 파일 업로드")
  public ApiResponse<UploadS3FileResponse> uploadS3File(@RequestPart MultipartFile file) {
    return ApiResponse.onSuccess(s3FileService.uploadS3File(file));
  }

  @DeleteMapping
  @Operation(summary = "S3 파일 삭제")
  public ApiResponse<Void> deleteS3File(@RequestParam String fileUrl) {
    s3FileService.deleteS3File(fileUrl);
    return ApiResponse.onSuccess();
  }
}
