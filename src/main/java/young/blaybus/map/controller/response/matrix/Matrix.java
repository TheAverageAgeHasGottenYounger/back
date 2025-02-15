package young.blaybus.map.controller.response.matrix;

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
@Schema(description = "SK Matrix 응답 객체")
@ToString
public class Matrix {

  @Schema(description = "거리")
  private double distance;

}
