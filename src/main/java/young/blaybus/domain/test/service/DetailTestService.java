package young.blaybus.domain.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.blaybus.api_response.exception.GeneralException;
import young.blaybus.api_response.status.ErrorStatus;
import young.blaybus.domain.test.controller.response.DetailTestResponse;
import young.blaybus.domain.test.repository.DetailTestRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailTestService {

  private final DetailTestRepository detailTestRepository;

  /**
   * 테스트 상세 조회
   */
  public DetailTestResponse getTest(Long testId) {
    DetailTestResponse response = detailTestRepository.getTest(testId);
    if (response == null) {
      throw new GeneralException(ErrorStatus.NOT_FOUND, "존재하지 않는 TEST ID입니다.");
    }
    return response;
  }
}
