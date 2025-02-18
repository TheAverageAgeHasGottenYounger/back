package young.blaybus.domain.matching.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import young.blaybus.util.enums.DayOfWeek;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@Schema(description = "매칭 요청 목록 조회 응답 객체")
public class GetMatchingSeniorListResponse {

    @Schema(description = "어르신 ID")
    private long seniorId;

    @Schema(description = "프로필 사진")
    private String profileUrl;

    @Schema(description = "어르신 이름")
    private String seniorName;

    @Schema(description = "주소")
    private String address;

    @Schema(description = "요일")
    private List<DayOfWeek> seniorDay;

    @Schema(description = "시작 시간")
    private String startTime;

    @Schema(description = "종료 시간")
    private String endTime;

    @Schema(description = "케어 스타일")
    private String careStyle;

    @Schema(description = "적합도")
    private int fitness;
}
