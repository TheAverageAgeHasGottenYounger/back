package young.blaybus.domain.center.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.center.service.CenterService;

@RestController
@RequiredArgsConstructor
@Tag(name = "센터 관련 API")
public class CenterController {

  private final CenterService centerService;

  @GetMapping("/health")
  public String healthCheck(){
    return "Hello World";
  }

  @GetMapping("/center/is-registration")
  @Operation(summary = "센터 등록 여부")
  public ApiResponse<?> idRegistration(@RequestParam String centerName) {
    return ApiResponse.onSuccess(centerService.isRegistrationCenterByName(centerName));
  }

}
