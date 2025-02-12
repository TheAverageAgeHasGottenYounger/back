package young.blaybus.domain.senior.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CareGrade {

  GRADE1("1등급"),
  GRADE2("2등급"),
  GRADE3("3등급"),
  GRADE4("4등급"),
  GRADE5("5등급"),
  GRADE6("인지지원 등급");

  private final String value;
}
