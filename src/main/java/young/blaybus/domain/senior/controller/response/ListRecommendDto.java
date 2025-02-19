package young.blaybus.domain.senior.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import young.blaybus.util.enums.CareStyle;
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

  @Setter
  @Schema(description = "희망 요일 목록")
  private List<DayOfWeek> dayList;

  @Schema(description = "희망 시작 시간")
  private LocalTime startTime;

  @Schema(description = "희망 종료 시간")
  private LocalTime endTime;

  @Schema(description = "돌봄 스타일")
  private CareStyle careStyle;

  @Schema(description = "적합도 %")
  private Integer fitness;

  public String getStartTime() {
    return startTime.format(DateTimeFormatter.ofPattern("a HH:mm").withLocale(Locale.forLanguageTag("ko")));
  }
  public String getEndTime() {
    return endTime.format(DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.forLanguageTag("ko")));
  }
  public String getCareStyle() {
    String emojiRegex = "[\uD83C-\uDBFF\uDC00-\uDFFF\u2600-\u26FF]+";
    return careStyle.getValue().replaceAll(emojiRegex, "").strip();
  }
}
