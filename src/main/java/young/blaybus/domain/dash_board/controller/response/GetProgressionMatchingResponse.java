package young.blaybus.domain.dash_board.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import young.blaybus.util.enums.DayOfWeek;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@Schema(description = "진행중인 매칭 조회 응답 객체")
public class GetProgressionMatchingResponse {

    @Schema(description = "프로필 사진")
    private String profileImageUrl;

    @Schema(description = "어르신 이름")
    private String seniorName;

    @Schema(description = "요일")
    private List<DayOfWeek> seniorDay;

    @Schema(description = "시작 시간")
    private String startTime;

    @Schema(description = "대기")
    private int atmosphere;

    @Schema(description = "수락")
    private int acceptance;

    @Schema(description = "거절")
    private int refusal;

    @Schema(description = "조율 요청")
    private int tuning;

}
