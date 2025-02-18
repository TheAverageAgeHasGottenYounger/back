package young.blaybus.domain.senior.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import young.blaybus.domain.job_search.response.JobSearchTimeSlotResponse;
import young.blaybus.util.enums.DayOfWeek;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "추천 요양 보호사 목록 조회 응답 DTO")
public class ListRecommendDto {

  @Schema(description = "요양 보호사 ID")
  private String memberId;

  @Schema(description = "이름")
  private String name;

  @Schema(description = "프로필 사진")
  private String profileUrl;

  @Schema(description = "근무 가능 요일&시간대 목록")
  private List<TimeSlotDto> timeSlots;

  @Schema(description = "돌봄 스타일")
  private String careStyle;

  @Schema(description = "적합도 %")
  private Integer fitness;

}
