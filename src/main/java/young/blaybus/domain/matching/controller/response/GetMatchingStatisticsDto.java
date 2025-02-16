package young.blaybus.domain.matching.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@Schema(description = "매칭 현황 (건수) 응답 객체")
public class GetMatchingStatisticsDto {

    @Schema(description = "전체 매칭")
    private int fullMatching;

    @Schema(description = "수락")
    private int acceptance;

    @Schema(description = "거절")
    private int refusal;

    @Schema(description = "조율 요청")
    private int tuning;
}
