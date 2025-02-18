package young.blaybus.domain.senior.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.blaybus.domain.senior.controller.response.DetailSeniorResponse;
import young.blaybus.domain.senior.repository.DetailSeniorRepository;
import young.blaybus.util.enums.DayOfWeek;
import young.blaybus.util.enums.assist.FoodAssist;
import young.blaybus.util.enums.assist.LifeAssist;
import young.blaybus.util.enums.assist.MoveAssist;
import young.blaybus.util.enums.assist.ToiletAssist;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailSeniorService {

  private final DetailSeniorRepository detailSeniorRepository;


  public DetailSeniorResponse getSenior(Long seniorId) {
    DetailSeniorResponse senior = detailSeniorRepository.getSenior(seniorId);
    List<DayOfWeek> seniorDayList = detailSeniorRepository.getSeniorDayList(seniorId);
    List<LifeAssist> lifeAssistList = detailSeniorRepository.getLifeAssistList(seniorId);
    List<FoodAssist> foodAssistList = detailSeniorRepository.getFoodAssistList(seniorId);
    List<MoveAssist> moveAssistList = detailSeniorRepository.getMoveAssistList(seniorId);
    List<ToiletAssist> toiletAssistList = detailSeniorRepository.getToiletAssistList(seniorId);

    senior.setDayList(seniorDayList);
    senior.setLifeAssistList(lifeAssistList);
    senior.setFoodAssistList(foodAssistList);
    senior.setMoveAssistList(moveAssistList);
    senior.setToiletAssistList(toiletAssistList);
    senior.setDayValueList(seniorDayList.stream().map(DayOfWeek::toString).toList());
    senior.setLifeAssistValueList(lifeAssistList.stream().map(LifeAssist::getValue).toList());
    senior.setFoodAssistValueList(foodAssistList.stream().map(FoodAssist::getValue).toList());
    senior.setMoveAssistValueList(moveAssistList.stream().map(MoveAssist::getValue).toList());
    senior.setToiletAssistValueList(toiletAssistList.stream().map(ToiletAssist::getValue).toList());

    return senior;
  }
}
