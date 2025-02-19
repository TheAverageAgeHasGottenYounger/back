package young.blaybus.domain.senior.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.blaybus.api_response.exception.GeneralException;
import young.blaybus.api_response.status.ErrorStatus;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.member.security.SecurityUtils;
import young.blaybus.domain.senior.controller.response.DetailMatchingSeniorResponse;
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
  private final MemberRepository memberRepository;


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
//    senior.setDayValueList(seniorDayList.stream().map(DayOfWeek::toString).toList());
//    senior.setLifeAssistValueList(lifeAssistList.stream().map(LifeAssist::getValue).toList());
//    senior.setFoodAssistValueList(foodAssistList.stream().map(FoodAssist::getValue).toList());
//    senior.setMoveAssistValueList(moveAssistList.stream().map(MoveAssist::getValue).toList());
//    senior.setToiletAssistValueList(toiletAssistList.stream().map(ToiletAssist::getValue).toList());

    return senior;
  }

  public DetailMatchingSeniorResponse getMatchingSenior(Long seniorId) {
    String currentMemberId = SecurityUtils.getCurrentMemberName();
    Member worker = memberRepository.findById(currentMemberId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.UNAUTHORIZED));

    DetailMatchingSeniorResponse response = detailSeniorRepository.getMatchingSenior(seniorId, worker);

    List<DayOfWeek> seniorDayList = detailSeniorRepository.getSeniorDayList(seniorId);
    response.setDayList(seniorDayList.stream().map(Enum::name).toList());

    List<LifeAssist> lifeAssistList = detailSeniorRepository.getLifeAssistList(seniorId);
    List<FoodAssist> foodAssistList = detailSeniorRepository.getFoodAssistList(seniorId);
    List<MoveAssist> moveAssistList = detailSeniorRepository.getMoveAssistList(seniorId);
    List<ToiletAssist> toiletAssistList = detailSeniorRepository.getToiletAssistList(seniorId);

    List<String> careList = lifeAssistList.stream().map(LifeAssist::getValue).collect(Collectors.toList());
    foodAssistList.stream().map(FoodAssist::getValue).forEach(careList::add);
    moveAssistList.stream().map(MoveAssist::getValue).forEach(careList::add);
    toiletAssistList.stream().map(ToiletAssist::getValue).forEach(careList::add);

    response.setCareList(careList);

    return response;
  }
}
