package young.blaybus.domain.senior.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.blaybus.domain.senior.controller.response.ListSeniorDto;
import young.blaybus.domain.senior.controller.response.ListSeniorResponse;
import young.blaybus.domain.senior.repository.ListSeniorRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListSeniorService {

  private final ListSeniorRepository listSeniorRepository;

  // todo centerId 전달

  public ListSeniorResponse getSeniorList() {
    List<ListSeniorDto> seniorList = listSeniorRepository.getSeniorList(1L);

    return ListSeniorResponse.builder()
      .seniorList(seniorList)
      .build();
  }
}
