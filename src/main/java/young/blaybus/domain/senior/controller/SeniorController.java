package young.blaybus.domain.senior.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.senior.controller.request.CreateSeniorRequest;
import young.blaybus.domain.senior.controller.request.UpdateSeniorRequest;
import young.blaybus.domain.senior.controller.response.DetailSeniorResponse;
import young.blaybus.domain.senior.controller.response.ListRecommendResponse;
import young.blaybus.domain.senior.controller.response.ListSeniorResponse;
import young.blaybus.domain.senior.service.RecommendService;
import young.blaybus.domain.senior.service.SeniorService;

@RestController
@RequestMapping("/senior")
@RequiredArgsConstructor
@Tag(name = "어르신 관련 API")
public class SeniorController {

  private final SeniorService seniorService;
  private final RecommendService recommendService;

  @GetMapping
  @Operation(summary = "어르신 목록 조회")
  public ApiResponse<ListSeniorResponse> getSeniorList() {
    return ApiResponse.onSuccess(seniorService.getSeniorList());
  }

  @GetMapping("/{senior-id}")
  @Operation(summary = "어르신 상세 조회")
  public ApiResponse<DetailSeniorResponse> getSenior(
    @PathVariable("senior-id") Long seniorId
  ) {
    return ApiResponse.onSuccess(seniorService.getSenior(seniorId));
  }

  @PostMapping
  @Operation(summary = "어르신 정보 등록")
  public ApiResponse<Void> createSenior(
    @RequestBody CreateSeniorRequest request
  ) {
    seniorService.createSenior(request);
    return ApiResponse.onSuccess();
  }

  @PatchMapping("/{senior-id}")
  @Operation(summary = "어르신 정보 수정(구인 정보 등록)")
  public ApiResponse<Void> updateSenior(
    @PathVariable("senior-id") Long seniorId,
    @RequestBody UpdateSeniorRequest request
  ) {
    seniorService.updateSenior(seniorId, request);
    return ApiResponse.onSuccess();
  }

  @GetMapping("/{senior-id}/recommend")
  @Operation(summary = "요양 보호사 추천 받기")
  public ApiResponse<ListRecommendResponse> getRecommendList(
    @PathVariable("senior-id") Long seniorId
  ) {
    return ApiResponse.onSuccess(recommendService.getRecommendList(seniorId));
  }

}
