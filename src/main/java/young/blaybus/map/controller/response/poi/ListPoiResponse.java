package young.blaybus.map.controller.response.poi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "POI 목록 응답 객체")
public class ListPoiResponse {

  private SearchPoiInfo searchPoiInfo;
}
