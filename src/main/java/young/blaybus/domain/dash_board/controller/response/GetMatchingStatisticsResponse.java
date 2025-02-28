package young.blaybus.domain.dash_board.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@Schema(description = "매칭 현황 통계 응답 객체")
public class GetMatchingStatisticsResponse {

    @Schema(description = "전체 매칭")
    private int fullMatching;

    @Schema(description = "신규 매칭")
    private int newMatching;
}
