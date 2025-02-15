package young.blaybus.map.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.blaybus.map.SkClient;
import young.blaybus.map.controller.request.SearchPoiRequest;
import young.blaybus.map.controller.response.geocoding.Coordinate;
import young.blaybus.map.controller.response.geocoding.GeocodingResponse;
import young.blaybus.map.controller.response.poi.ListPoiResponse;
import young.blaybus.map.controller.response.matrix.MatrixResponse;
import young.blaybus.map.controller.response.poi.PoiDto;

@Service
@RequiredArgsConstructor
@Transactional
public class MapService {

  private final SkClient skClient;

  private final static int version = 1;
  private final static int count = 1;
  private final static String addressFlag = "F00";
  private final static String coordType = "WGS84GEO";

  @Value("${sk.app-key}")
  private String appKey;

  /**
   * 주소 -> 위도/경도 변환
   */
  public Coordinate geocoding(String cityDo, String guGun, String dong) {

    // 상세주소 받게되면 dong 뒤에 맨 앞 공백과 함께 추가한 뒤 붙여서 요청
    GeocodingResponse response = skClient.geocoding(
      appKey, version, cityDo + " " + guGun + " " + dong, addressFlag, coordType, count
    );

    return response.getCoordinateInfo().getCoordinate().get(0);
  }

  /**
   * 좌표 간 거리 계산
   */
  public Double getDistance(Coordinate start, Coordinate end) {
    String requestBody = String.format(
      "{\"origins\":[{\"lon\":\"%f\",\"lat\":\"%f\"}],\"destinations\":[{\"lon\":\"%f\",\"lat\":\"%f\"}]}",
      start.getLon(), start.getLat(), end.getLon(), end.getLat()
    );

    MatrixResponse matrix = skClient.matrix(appKey, version, requestBody);
    return matrix.getMatrix().get(0).getDistance();

  }

  public ListPoiResponse getPoiList(SearchPoiRequest request) {

    ListPoiResponse response = skClient.poi(appKey, version, request.address());
    for (PoiDto poi : response.getSearchPoiInfo().getPoiResponse().getPoiList()) {
      System.out.println("poi = " + poi);
    }
    return response;
  }
}
