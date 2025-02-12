package young.blaybus.util.enums.assist;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MoveAssist {

  GRADE1("거동 불가"),
  GRADE2("휠체어 이동 보조"),
  GRADE3("이동시 부축 도움"),
  GRADE4("스스로 거동 가능");

  private final String value;
}
