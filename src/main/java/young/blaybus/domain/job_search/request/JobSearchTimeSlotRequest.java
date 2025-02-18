package young.blaybus.domain.job_search.request;

import io.swagger.v3.oas.annotations.media.Schema;
import young.blaybus.util.enums.DayOfWeek;

@Schema(description = "근무 가능 시간대 요청 객체")
public record JobSearchTimeSlotRequest(
        @Schema(description = "근무 요일")
        DayOfWeek day,

        @Schema(description = "근무 시작 시간 (HH:mm)")
        String startTime,

        @Schema(description = "근무 종료 시간 (HH:mm)")
        String endTime
){
}
