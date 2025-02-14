package young.blaybus.domain.senior.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "어르신 목록 조회 응답 객체")
public class ListSeniorResponse {

  @Default
  @Schema(description = "어르신 목록")
  private List<ListSeniorDto> seniorList = new ArrayList<>();

}
