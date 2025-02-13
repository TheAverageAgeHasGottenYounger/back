package young.blaybus.map.response;

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
@Schema(description = "Matrix 응답 객체")
public class MatrixResponse {

  @JsonProperty("matrixRoutes")
  private List<Matrix> matrix;
}
