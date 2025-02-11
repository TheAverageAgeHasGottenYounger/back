package young.blaybus.domain.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.blaybus.domain.test.Test;
import young.blaybus.domain.test.controller.request.CreateTestRequest;
import young.blaybus.domain.test.repository.TestRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateTestService {

  private final TestRepository testRepository;

  /**
   * 테스트 생성
   */
  public void createTest(CreateTestRequest request) {
    testRepository.save(
      Test.builder()
        .title(request.title())
        .content(request.content())
        .build()
    );
  }
}
