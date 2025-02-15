package young.blaybus.map.controller.response.poi;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SearchPoiInfo {

  @JsonProperty("pois")
  private ListPoiDto poiResponse;

  private int count;
}
