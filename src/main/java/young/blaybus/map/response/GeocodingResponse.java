package young.blaybus.map.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Geocoding 응답 객체")
public class GeocodingResponse {

  private CoordinateInfo coordinateInfo;
}
