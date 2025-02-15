package young.blaybus.domain.job_search.request;

import io.swagger.v3.oas.annotations.media.Schema;
import young.blaybus.util.enums.DayOfWeek;

import java.util.List;

@Schema(description = "구직 정보 수정 객체")
public record UpdateJobSearchRequest(
        @Schema(description = "근무 시작 시간 (09:30)")
        String startTime,

        @Schema(description = "근무 종료 시간(18:00)")
        String endTime,

        @Schema(description = "희망 시급")
        Integer salary,

        @Schema(description = "근무 가능 지역")
        List<JobSearchAreaRequest> jobSearchAreas,

        @Schema(description = "근무 가능 요일 목록")
        List<DayOfWeek> dayList
) {
}
