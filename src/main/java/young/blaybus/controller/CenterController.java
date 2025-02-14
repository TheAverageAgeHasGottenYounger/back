package young.blaybus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.center.service.CenterService;

@RestController
@RequiredArgsConstructor
public class CenterController {

  private final CenterService centerService;

  @GetMapping("/health")
  public String healthCheck(){
    return "Hello World";
  }

  @GetMapping("/center/is-registration")
  public ApiResponse<?> idRegistration(@RequestParam String centerName) {
    return ApiResponse.onSuccess(centerService.isRegistrationCenterByName(centerName));
  }

}
