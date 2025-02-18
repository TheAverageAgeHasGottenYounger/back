package young.blaybus.domain.center.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.center.controller.response.GetCenter;
import young.blaybus.domain.center.controller.response.GetCenterDetailInforResponse;
import young.blaybus.domain.center.controller.response.GetCenterResponse;
import young.blaybus.domain.center.service.CenterService;

@RestController
@RequiredArgsConstructor
@Tag(name = "센터 관련 API")
public class CenterController {

  private final CenterService centerService;

  @GetMapping("/center/is-registration")
  @Operation(summary = "센터 등록 여부")
  @ApiResponses(value = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
                  responseCode = "200",
                  content = @Content(schema = @Schema(implementation = GetCenter.class))
          )
  })
  public ApiResponse<?> idRegistration(@RequestParam String centerName) {
    return ApiResponse.onSuccess(centerService.isRegistrationCenterByName(centerName));
  }

  @GetMapping("/center")
  @Operation(summary = "센터 등록")
  @ApiResponses(value = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
                  responseCode = "200",
                  content = @Content(schema = @Schema(implementation = GetCenterResponse.class))
          )
  })
  public ApiResponse<GetCenterResponse> getCenterRegistrationAndCreate1(@RequestParam String centerName) {
      return ApiResponse.onSuccess(centerService.registerCenter(centerName));
  }

  @GetMapping("/center/check")
  @Operation(summary = "센터 조회")
  @ApiResponses(value = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
                  responseCode = "200",
                  content = @Content(schema = @Schema(implementation = GetCenterResponse.class))
          )
  })
  public ApiResponse<GetCenterResponse> getCenterCheck(@RequestParam String centerName) {
    return ApiResponse.onSuccess(centerService.getCenterCheck(centerName));
  }

  @GetMapping("/center/detail")
  @Operation(summary = "센터 상세 조회")
  @ApiResponses(value = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
                  responseCode = "200",
                  content = @Content(schema = @Schema(implementation = GetCenterDetailInforResponse.class))
          )
  })
  public ApiResponse<GetCenterDetailInforResponse> getCenterDetailInfor(@RequestParam String centerName) {
    return ApiResponse.onSuccess(centerService.getCenterDetailInfor(centerName));
  }

}
