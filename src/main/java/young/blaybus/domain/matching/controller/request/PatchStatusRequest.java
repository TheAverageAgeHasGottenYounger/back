package young.blaybus.domain.matching.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import young.blaybus.domain.matching.enums.MatchingStatus;

@Schema(description = "수락/거절/조율요청 상태 요청 객체")
public record PatchStatusRequest(

    @Schema(description = "어르신 ID")
    String seniorId,

    @Schema(description = "매칭 상태")
    MatchingStatus status
) { }
