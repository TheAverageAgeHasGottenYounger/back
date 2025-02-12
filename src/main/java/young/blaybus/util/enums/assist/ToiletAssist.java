package young.blaybus.util.enums.assist;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ToiletAssist {

  GRADE1("유치도뇨/방광루/장루 관리"),
  GRADE2("기저귀 케어 필요"),
  GRADE3("가끔 대소변 실수 시 도움"),
  GRADE4("스스로 배변 가능");

  private final String value;
}
