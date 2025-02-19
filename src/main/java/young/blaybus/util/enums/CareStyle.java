package young.blaybus.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CareStyle {

  STYLE1("📖  메뉴얼과 규칙을 중요시하는 꼼꼼형"),
  STYLE2("😌  조용하고 신뢰있게 돕는 차분형"),
  STYLE3("⚖️  필요에 따라 유연하게 조정하는 균형형"),
  STYLE4("🤝  감정에 공감하는 정서 교감형"),
  STYLE5("🧒  친근한 가족같이 적극적인 돌봄");

  private final String value;
}
