package young.blaybus.domain.job_search.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = " 요양보호사의 구직 정보 상세 조회 응답 객체")
public class DetailJobSearchResponse {
  @Schema(description = "구직 정보 ID")
  private Long jobSearchId;
  @Schema(description = "근무 시작 시간")
  private String startTime;

  @Schema(description = "근무 종료 시간")
  private String endTime;

  @Schema(description = "희망 급여")
  private Integer salary;

  @Schema(description = "근무 가능 지역 목록")
  private List<JobSearchAreaResponse> jobSearchAreas;

  @Schema(description = "근무 가능 요일 목록")
  private List<String> dayList;
}
