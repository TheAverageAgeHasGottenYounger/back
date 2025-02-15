package young.blaybus.domain.matching.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import young.blaybus.domain.matching.enums.MatchingStatus;
import young.blaybus.domain.member.controller.response.GetMember;

@Getter
@Builder
@ToString
@AllArgsConstructor
@Schema(description = "매칭 현황 조회 객체")
public class GetMatching {

    @Schema(description = "매칭 현황 ID")
    private long matchingId;

    @Schema(description = "어르신 기본 정보 객체")
    private GetSenior senior;

    @Schema(description = "요양보호사 객체")
    private GetMember member;

    @Schema(description = "매칭 상태")
    private MatchingStatus matchingStatus;
}
