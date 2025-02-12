package young.blaybus.util.enums.assist;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FoodAssist {

  GRADE1("경관식 보조"),
  GRADE2("죽, 반찬 등 요리 필요"),
  GRADE3("식사 차려드리기"),
  GRADE4("스스로 식사 가능");

  private final String value;
}
