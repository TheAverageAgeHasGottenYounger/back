package young.blaybus.domain.senior.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import young.blaybus.domain.senior.enums.CareGrade;
import young.blaybus.domain.senior.enums.Sex;
import young.blaybus.util.enums.DayOfWeek;
import young.blaybus.util.enums.assist.FoodAssist;
import young.blaybus.util.enums.assist.LifeAssist;
import young.blaybus.util.enums.assist.MoveAssist;
import young.blaybus.util.enums.assist.ToiletAssist;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "어르신 상세 조회 응답 객체")
public class DetailSeniorResponse {

  private long seniorId;

  private String profileUrl;

  private String name;

  private Sex sex;

  private LocalDate birthday;

  private String address;

  private Integer salary;

  private CareGrade careGrade;

//  @Setter
//  private List<String> dayValueList;
//
//  @Setter
//  private List<String> foodAssistValueList;
//
//  @Setter
//  private List<String> moveAssistValueList;
//
//  @Setter
//  private List<String> lifeAssistValueList;
//
//  @Setter
//  private List<String> toiletAssistValueList;

  @Setter
  private List<DayOfWeek> dayList;

  @Setter
  private List<FoodAssist> foodAssistList;

  @Setter
  private List<MoveAssist> moveAssistList;

  @Setter
  private List<LifeAssist> lifeAssistList;

  @Setter
  private List<ToiletAssist> toiletAssistList;

  private LocalTime startTime;

  private LocalTime endTime;

  public String getCareGrade() {
    return careGrade.getValue();
  }
}
