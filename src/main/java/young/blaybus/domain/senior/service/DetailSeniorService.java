package young.blaybus.domain.senior.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.blaybus.domain.senior.controller.response.DetailSeniorResponse;
import young.blaybus.domain.senior.repository.DetailSeniorRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailSeniorService {

  private final DetailSeniorRepository detailSeniorRepository;


  public DetailSeniorResponse getSenior(Long seniorId) {
    DetailSeniorResponse senior = detailSeniorRepository.getSenior(seniorId);
    senior.setDayList(detailSeniorRepository.getSeniorDayList(seniorId));
    senior.setLifeAssistList(detailSeniorRepository.getLifeAssistList(seniorId));
    senior.setFoodAssistList(detailSeniorRepository.getFoodAssistList(seniorId));
    senior.setMoveAssistList(detailSeniorRepository.getMoveAssistList(seniorId));
    senior.setToiletAssistList(detailSeniorRepository.getToiletAssistList(seniorId));

    return senior;
  }
}
