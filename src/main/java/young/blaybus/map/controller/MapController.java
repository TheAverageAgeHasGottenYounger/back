package young.blaybus.map.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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

  @GetMapping("/map-file")
  @Operation(summary = "시군구 저장")
  public ApiResponse<Void> getMapFile() {
    mapService.readMapExcel();
    return ApiResponse.onSuccess();
  }


  @GetMapping("/city")
  @Operation(summary = "시 목록 조회")
  public ApiResponse<List<String>> getCityList() {
    return ApiResponse.onSuccess(mapService.getCityList());
  }


  @GetMapping("/gu-gun")
  @Operation(summary = "구/군 목록 조회")
  public ApiResponse<List<String>> getGuGunList(String city) {
    return ApiResponse.onSuccess(mapService.getGuGunList(city));
  }


  @GetMapping("/dong")
  @Operation(summary = "동 목록 조회")
  public ApiResponse<List<String>> getDongList(String guGun) {
    return ApiResponse.onSuccess(mapService.getDongList(guGun));
  }


}
