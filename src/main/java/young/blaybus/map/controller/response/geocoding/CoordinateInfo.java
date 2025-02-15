package young.blaybus.map.controller.response.geocoding;

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
@Schema(description = "SK Geocoding 응답 객체")
public class CoordinateInfo {

  @Schema(description = "Geocoding")
  private List<Coordinate> coordinate;

}
