package young.blaybus.domain.senior.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import young.blaybus.domain.senior.enums.CareGrade;
import young.blaybus.domain.senior.enums.Sex;
import young.blaybus.util.enums.CareStyle;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "어르신 상세 조회 응답 객체")
public class DetailMatchingSeniorResponse {

  private long seniorId;

  private String profileUrl;

  private String name;

  private Sex sex;

  private LocalDate birthday;

  private String address;

  private Integer salary;

  private CareGrade careGrade;

  private CareStyle careStyle;

  @Setter
  private List<String> dayList;

  @Setter
  @Default
  private List<String> careList = new ArrayList<>();

  private LocalTime startTime;

  private LocalTime endTime;

  @Setter
  private Integer fitness;

  private String phoneNumber;


  public String getStartTime() {
    return startTime.format(DateTimeFormatter.ofPattern("a HH:mm").withLocale(Locale.forLanguageTag("ko")));
  }
  public String getEndTime() {
    return endTime.format(DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.forLanguageTag("ko")));
  }

  public String getCareGrade() {
    return careGrade.getValue();
  }

  public String getCareStyle() {
    String emojiRegex = "[\uD83C-\uDBFF\uDC00-\uDFFF\u2600-\u26FF]+";
    return careStyle.getValue().replaceAll(emojiRegex, "").strip();
  }
}
