package young.blaybus.map.controller.response.geocoding;

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
@Schema(description = "SK Geocoding 응답 객체")
@ToString
public class Coordinate {

  @Schema(description = "위도")
  private double lat;

  @Schema(description = "경도")
  private double lon;

  @Schema(description = "시/도")
  @JsonProperty("city_do")
  private String cityDo;

  @Schema(description = "군/구")
  @JsonProperty("gu_gun")
  private String guGun;

  @Schema(description = "동")
  @JsonProperty("legalDong")
  private String dong;

}
