package young.blaybus.domain.senior.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import young.blaybus.domain.senior.enums.CareGrade;
import young.blaybus.domain.senior.enums.Sex;
import young.blaybus.util.enums.CareStyle;
import young.blaybus.util.enums.DayOfWeek;
import young.blaybus.util.enums.assist.FoodAssist;
import young.blaybus.util.enums.assist.LifeAssist;
import young.blaybus.util.enums.assist.MoveAssist;
import young.blaybus.util.enums.assist.ToiletAssist;

@Schema(description = "어르신 등록 요청 객체")
public record CreateSeniorRequest(

  String profileUrl,

  String name,

  LocalDate birthday,

  Sex sex,

  String address,

  @Schema(description = "시작 시간", example = "12:00:00")
  LocalTime startTime,

  @Schema(description = "종료 시간", example = "12:00:00")
  LocalTime endTime,

  List<DayOfWeek> dayList,

  List<FoodAssist> foodAssistList,

  List<ToiletAssist> toiletAssistList,

  List<MoveAssist> moveAssistList,

  List<LifeAssist> lifeAssistList,

  CareStyle careStyle,

  CareGrade careGrade

) {

}
