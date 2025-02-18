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
@Schema(description = "추천 요양 보호사 목록 조회 응답 객체")
public class ListRecommendResponse {

  @Default
  @Schema(description = "요양 보호사 목록 조회 응답 객체")
  private List<ListRecommendDto> recommendList = new ArrayList<>();
}
