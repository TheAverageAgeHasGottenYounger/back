package young.blaybus.domain.senior.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.blaybus.api_response.exception.GeneralException;
import young.blaybus.api_response.status.ErrorStatus;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.member.security.SecurityUtils;
import young.blaybus.domain.senior.controller.response.ListSeniorDto;
import young.blaybus.domain.senior.controller.response.ListSeniorResponse;
import young.blaybus.domain.senior.repository.ListSeniorRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListSeniorService {

  private final ListSeniorRepository listSeniorRepository;
  private final MemberRepository memberRepository;


  public ListSeniorResponse getSeniorList() {
    String currentMemberId = SecurityUtils.getCurrentMemberName();
    Member member = memberRepository.findById(currentMemberId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.UNAUTHORIZED));

    List<ListSeniorDto> seniorList = listSeniorRepository.getSeniorList(member.getCenter());

    return ListSeniorResponse.builder()
      .seniorList(seniorList)
      .build();
  }
}
