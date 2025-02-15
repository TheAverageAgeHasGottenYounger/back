package young.blaybus.domain.enum_api.service;

import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.blaybus.domain.enum_api.controller.response.EnumDto;
import young.blaybus.domain.enum_api.controller.response.EnumResponse;
import young.blaybus.util.enums.CareStyle;
import young.blaybus.util.enums.DayOfWeek;
import young.blaybus.util.enums.assist.FoodAssist;
import young.blaybus.util.enums.assist.LifeAssist;
import young.blaybus.util.enums.assist.MoveAssist;
import young.blaybus.util.enums.assist.ToiletAssist;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnumService {

  /**
   * 요일 목록 조회
   */
  public EnumResponse getDayList() {
    List<EnumDto> dayList =
      Stream.of(DayOfWeek.values())
        .map(value -> EnumDto.builder()
          .code(value.name())
          .build()
        )
        .toList();

    return EnumResponse.builder()
      .enumList(dayList)
      .build();
  }

  /**
   * 식사 보조 목록 조회
   */
  public EnumResponse getFoodAssistList() {
    List<EnumDto> foodAssistList =
      Stream.of(FoodAssist.values())
        .map(value -> EnumDto.builder()
          .code(value.name())
          .value(value.getValue())
          .build()
        )
        .toList();

    return EnumResponse.builder()
      .enumList(foodAssistList)
      .build();
  }

  /**
   * 일상 보조 목록 조회
   */
  public EnumResponse getLifeAssistList() {
    List<EnumDto> lifeAssistList =
      Stream.of(LifeAssist.values())
        .map(value -> EnumDto.builder()
          .code(value.name())
          .value(value.getValue())
          .build()
        )
        .toList();

    return EnumResponse.builder()
      .enumList(lifeAssistList)
      .build();
  }

  /**
   * 이동 보조 목록 조회
   */
  public EnumResponse getMoveAssistList() {
    List<EnumDto> moveAssistList =
      Stream.of(MoveAssist.values())
        .map(value -> EnumDto.builder()
          .code(value.name())
          .value(value.getValue())
          .build()
        )
        .toList();

    return EnumResponse.builder()
      .enumList(moveAssistList)
      .build();
  }

  /**
   * 배변 보조 목록 조회
   */
  public EnumResponse getToiletAssistList() {
    List<EnumDto> toiletAssistList =
      Stream.of(ToiletAssist.values())
        .map(value -> EnumDto.builder()
          .code(value.name())
          .value(value.getValue())
          .build()
        )
        .toList();

    return EnumResponse.builder()
      .enumList(toiletAssistList)
      .build();
  }

  /**
   * 요양 스타일 목록 조회
   */
  public EnumResponse getCareStyleList() {
    List<EnumDto> careStyleList =
      Stream.of(CareStyle.values())
        .map(value -> EnumDto.builder()
          .code(value.name())
          .value(value.getValue())
          .build()
        )
        .toList();

    return EnumResponse.builder()
      .enumList(careStyleList)
      .build();
  }
}
