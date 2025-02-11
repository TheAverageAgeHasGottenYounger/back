package young.blaybus.domain.test.controller.response;

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
@Schema(description = "테스트 목록 조회 응답 객체")
public class ListTestResponse {

  @Schema(description = "테스트 목록")
  private List<ListTestDto> testList;
}
