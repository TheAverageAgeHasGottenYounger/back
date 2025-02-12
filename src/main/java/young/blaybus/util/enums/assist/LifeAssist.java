package young.blaybus.util.enums.assist;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LifeAssist {

  CLEANING("청소,빨래 보조"),
  BATH("목욕 보조"),
  HOSPITAL("병원 동행"),
  WORKOUT("산책, 간단한 운동"),
  TALK("말벗 등 정서지원"),
  COGNITIVE("인지자극 활동");

  private final String value;
}
