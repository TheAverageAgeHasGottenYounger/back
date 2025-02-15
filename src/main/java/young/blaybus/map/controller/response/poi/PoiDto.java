
package young.blaybus.map.controller.response.poi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "POI 응답 DTO")
@ToString
public class PoiDto {

  private Long id;

  private String name;

  @JsonProperty("noorLat")
  private double lat;

  @JsonProperty("noorLon")
  private double lon;

  @JsonProperty("upperAddrName")
  private String cityDo;

  @JsonProperty("middleAddrName")
  private String guGun;

  @JsonProperty("lowerAddrName")
  private String dong;

}
