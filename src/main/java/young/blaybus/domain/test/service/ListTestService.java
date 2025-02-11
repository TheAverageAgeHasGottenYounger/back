package young.blaybus.domain.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import young.blaybus.domain.test.controller.response.ListTestResponse;
import young.blaybus.domain.test.repository.ListTestRepository;

@Service
@RequiredArgsConstructor
public class ListTestService {

  private final ListTestRepository listTestRepository;

  /**
   * 테스트 목록 조회
   */
  public ListTestResponse getTestList() {
    return ListTestResponse.builder()
      .testList(listTestRepository.getTestList())
      .build();
  }
}
