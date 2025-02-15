package young.blaybus.map.controller.response.poi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "POI 응답 DTO")
public class ListPoiDto {

  @JsonProperty("poi")
  private List<PoiDto> poiList;

}
