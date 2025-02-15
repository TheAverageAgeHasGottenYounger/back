package young.blaybus.map.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "POI 검색 요청 객체")
public record SearchPoiRequest(

  String address
) {

}
