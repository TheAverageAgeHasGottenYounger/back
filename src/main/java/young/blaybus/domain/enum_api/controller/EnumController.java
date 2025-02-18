package young.blaybus.domain.enum_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.enum_api.controller.response.EnumResponse;
import young.blaybus.domain.enum_api.service.EnumService;

@RestController
@RequestMapping("/enum")
@RequiredArgsConstructor
@Tag(name = "ENUM(선택형 필드) 관련 API")
public class EnumController {

  private final EnumService enumService;

  @GetMapping("/day")
  @Operation(summary = "요일 목록 조회")
  public ApiResponse<EnumResponse> getDayList() {
    return ApiResponse.onSuccess(enumService.getDayList());
  }

  @GetMapping("/food-assist")
  @Operation(summary = "식사 보조 등급 목록 조회")
  public ApiResponse<EnumResponse> getFoodAssistList() {
    return ApiResponse.onSuccess(enumService.getFoodAssistList());
  }

  @GetMapping("/life-assist")
  @Operation(summary = "일상 생활 보조 목록 조회")
  public ApiResponse<EnumResponse> getListAssistList() {
    return ApiResponse.onSuccess(enumService.getLifeAssistList());
  }

  @GetMapping("/move-assist")
  @Operation(summary = "이동 보조 등급 목록 조회")
  public ApiResponse<EnumResponse> getMoveAssistList() {
    return ApiResponse.onSuccess(enumService.getMoveAssistList());
  }

  @GetMapping("/toilet-assist")
  @Operation(summary = "배변 보조 등급 목록 조회")
  public ApiResponse<EnumResponse> getToiletAssistList() {
    return ApiResponse.onSuccess(enumService.getToiletAssistList());
  }

  @GetMapping("/care-style")
  @Operation(summary = "요양 스타일 목록 조회")
  public ApiResponse<EnumResponse> getCareStyleList() {
    return ApiResponse.onSuccess(enumService.getCareStyleList());
  }

  @GetMapping("/certificate-type")
  @Operation(summary = "자격증 유형 목록 조회")
  public ApiResponse<EnumResponse> getCertificateTypeList() {
    return ApiResponse.onSuccess(enumService.getCertificateTypeList());
  }

  @GetMapping("/care-grade")
  @Operation(summary = "장기요양 등급 목록 조회")
  public ApiResponse<EnumResponse> getCareGradeList() {
    return ApiResponse.onSuccess(enumService.getCareGradeList());
  }



}
