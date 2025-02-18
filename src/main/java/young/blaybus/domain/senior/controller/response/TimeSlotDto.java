package young.blaybus.domain.senior.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import young.blaybus.util.enums.DayOfWeek;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(description = "근무 요일&시간대 DTO")
public class TimeSlotDto {
  @Schema(description = "근무 요일")
  DayOfWeek day;

  @Schema(description = "근무 시작 시간 (HH:mm)")
  String startTime;

  @Schema(description = "근무 종료 시간 (HH:mm)")
  String endTime;
}
