package young.blaybus.map.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.map.controller.request.SearchPoiRequest;
import young.blaybus.map.controller.response.poi.ListPoiResponse;
import young.blaybus.map.service.MapService;

@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
@Tag(name = "지도 관련 API")
public class MapController {

  private final MapService mapService;

  @GetMapping("/search")
  @Operation(summary = "주소로 검색해 POI 목록 조회")
  public ApiResponse<ListPoiResponse> getPoiList(SearchPoiRequest request) {
    return ApiResponse.onSuccess(mapService.getPoiList(request));
  }

}
