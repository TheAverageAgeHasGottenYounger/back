package young.blaybus.domain.senior.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import young.blaybus.api_response.exception.GeneralException;
import young.blaybus.api_response.status.ErrorStatus;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.member.security.SecurityUtils;
import young.blaybus.domain.senior.Senior;
import young.blaybus.domain.senior.SeniorDay;
import young.blaybus.domain.senior.SeniorFoodAssist;
import young.blaybus.domain.senior.SeniorLifeAssist;
import young.blaybus.domain.senior.SeniorMoveAssist;
import young.blaybus.domain.senior.SeniorToiletAssist;
import young.blaybus.domain.senior.controller.request.CreateSeniorRequest;
import young.blaybus.domain.senior.controller.request.UpdateSeniorRequest;
import young.blaybus.domain.senior.repository.SeniorRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateSeniorService {

  private final SeniorRepository seniorRepository;
  private final MemberRepository memberRepository;

  public void createSenior(CreateSeniorRequest request) {

    String currentMemberId = SecurityUtils.getCurrentMemberName();
    Member member = memberRepository.findById(currentMemberId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.UNAUTHORIZED));

    String profileUrl = "https://theaverageagegottenyounger.s3.ap-northeast-2.amazonaws.com/blaybus-basic-profile-image.png";
    if (StringUtils.hasText(request.profileUrl())) profileUrl = request.profileUrl();

    Senior senior = Senior.builder()
      .name(request.name())
      .birthday(request.birthday())
      .sex(request.sex())
      .address(request.address())
      .profileUrl(profileUrl)
      .startTime(request.startTime())
      .endTime(request.endTime())
      .careStyle(request.careStyle())
      .careGrade(request.careGrade())
      .center(member.getCenter())
      .build();

    seniorRepository.save(senior);

    // 어르신 희망 요일
    request.dayList().forEach(day ->
      senior.getDayList().add(
        SeniorDay.builder()
          .senior(senior)
          .day(day)
          .build()
      ));

    // 어르신 식사 보조
    request.foodAssistList().forEach(foodAssist ->
      senior.getFoodAssistList().add(
        SeniorFoodAssist.builder()
          .senior(senior)
          .foodAssist(foodAssist)
          .build()
      ));

    // 어르신 이동 보조
    request.moveAssistList().forEach(moveAssist ->
      senior.getMoveAssistList().add(
        SeniorMoveAssist.builder()
          .senior(senior)
          .moveAssist(moveAssist)
          .build()
      ));

    // 어르신 일상 보조
    request.lifeAssistList().forEach(lifeAssist ->
      senior.getLifeAssistList().add(
        SeniorLifeAssist.builder()
          .senior(senior)
          .lifeAssist(lifeAssist)
          .build()
      ));

    // 어르신 배변 보조
    request.toiletAssistList().forEach(toiletAssist ->
      senior.getToiletAssistList().add(
        SeniorToiletAssist.builder()
          .senior(senior)
          .toiletAssist(toiletAssist)
          .build()
      ));
  }

  public void updateSenior(Long seniorId, UpdateSeniorRequest request) {
    Senior senior = seniorRepository.findById(seniorId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 어르신 ID입니다."));

    String profileUrl = senior.getProfileUrl();
    if (StringUtils.hasText(request.profileUrl())) profileUrl = request.profileUrl();

    senior.update(
      request.name(),
      request.birthday(),
      request.sex(),
      request.address(),
      profileUrl,
      request.startTime(),
      request.endTime(),
      request.careStyle(),
      request.salary()
    );


    senior.getDayList().clear();
    senior.getLifeAssistList().clear();
    senior.getFoodAssistList().clear();
    senior.getToiletAssistList().clear();
    senior.getMoveAssistList().clear();
    // 어르신 희망 요일
    request.dayList().forEach(day ->
      senior.getDayList().add(
        SeniorDay.builder()
          .senior(senior)
          .day(day)
          .build()
      ));

    // 어르신 식사 보조
    request.foodAssistList().forEach(foodAssist ->
      senior.getFoodAssistList().add(
        SeniorFoodAssist.builder()
          .senior(senior)
          .foodAssist(foodAssist)
          .build()
      ));

    // 어르신 이동 보조
    request.moveAssistList().forEach(moveAssist ->
      senior.getMoveAssistList().add(
        SeniorMoveAssist.builder()
          .senior(senior)
          .moveAssist(moveAssist)
          .build()
      ));

    // 어르신 일상 보조
    request.lifeAssistList().forEach(lifeAssist ->
      senior.getLifeAssistList().add(
        SeniorLifeAssist.builder()
          .senior(senior)
          .lifeAssist(lifeAssist)
          .build()
      ));

    // 어르신 배변 보조
    request.toiletAssistList().forEach(toiletAssist ->
      senior.getToiletAssistList().add(
        SeniorToiletAssist.builder()
          .senior(senior)
          .toiletAssist(toiletAssist)
          .build()
      ));

  }
}
