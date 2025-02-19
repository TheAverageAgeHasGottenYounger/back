package young.blaybus.domain.job_search.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import lombok.Setter;
import young.blaybus.domain.certificate.controller.response.ListCertificateDto;
import young.blaybus.util.enums.CareStyle;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = " 요양보호사의 구직 정보 상세 조회 응답 객체")
public class DetailJobSearchResponse {
  @Schema(description = "구직 정보 ID")
  private Long jobSearchId;

  @Schema(description = "보호사 이름")
  private String name;

  @Schema(description = "근무 시작 시간")
  private LocalTime startTime;

  @Schema(description = "근무 종료 시간")
  private LocalTime endTime;

  @Schema(description = "돌봄 스타일")
  private CareStyle careStyle;

  @Schema(description = "희망 급여")
  private Integer salary;

  @Default
  @Schema(description = "근무 가능 지역 목록")
  private List<JobSearchAreaResponse> jobSearchAreas = new ArrayList<>();

  @Schema(description = "근무 가능 요일 목록")
  private List<String> dayList;

  @Default
  @Schema(description = "자격증 목록")
  private List<ListCertificateDto> certificateList = new ArrayList<>();

  @Schema(description = "경력 기간")
  private String careerPeriod;

  @Schema(description = "주요 경력")
  private String career;

  @Schema(description = "한 줄 소개")
  private String introduction;

  @Setter
  @Schema(description = "적합도")
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
