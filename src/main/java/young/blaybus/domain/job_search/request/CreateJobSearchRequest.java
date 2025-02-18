package young.blaybus.domain.job_search.request;

import io.swagger.v3.oas.annotations.media.Schema;
import young.blaybus.util.enums.DayOfWeek;

import java.util.List;

@Schema(description = "구직 정보 입력 객체")
public record CreateJobSearchRequest(

        @Schema(description = "회원 ID")
        String memberId,

        @Schema(description = "희망 시급")
        Integer salary,

        @Schema(description = "근무 가능 지역")
        List<JobSearchAreaRequest> jobSearchAreas,

        @Schema(description = "근무 가능 요일&시간대 목록")
        List<JobSearchTimeSlotRequest> timeSlots
) {
}
