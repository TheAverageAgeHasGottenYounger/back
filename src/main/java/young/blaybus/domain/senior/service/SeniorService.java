package young.blaybus.domain.senior.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import young.blaybus.domain.senior.controller.request.CreateSeniorRequest;
import young.blaybus.domain.senior.controller.request.UpdateSeniorRequest;
import young.blaybus.domain.senior.controller.response.DetailMatchingSeniorResponse;
import young.blaybus.domain.senior.controller.response.DetailSeniorResponse;
import young.blaybus.domain.senior.controller.response.ListSeniorResponse;

@Service
@RequiredArgsConstructor
public class SeniorService {

  private final CreateSeniorService createSeniorService;
  private final ListSeniorService listSeniorService;
  private final DetailSeniorService detailSeniorService;

  public ListSeniorResponse getSeniorList() {
    return listSeniorService.getSeniorList();
  }

  public DetailSeniorResponse getSenior(Long seniorId) {
    return detailSeniorService.getSenior(seniorId);
  }

  public void createSenior(CreateSeniorRequest request) {
    createSeniorService.createSenior(request);
  }

  public void updateSenior(Long seniorId, UpdateSeniorRequest request) {
    createSeniorService.updateSenior(seniorId, request);
  }

  public DetailMatchingSeniorResponse getMatchingSenior(Long seniorId) {
    return detailSeniorService.getMatchingSenior(seniorId);
  }
}
