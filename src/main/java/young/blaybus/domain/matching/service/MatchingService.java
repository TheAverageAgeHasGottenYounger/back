package young.blaybus.domain.matching.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import young.blaybus.domain.matching.Matching;
import young.blaybus.domain.matching.controller.request.PatchStatusRequest;
import young.blaybus.domain.matching.enums.MatchingStatus;
import young.blaybus.domain.matching.repository.MatchingRepository;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.senior.Senior;
import young.blaybus.domain.senior.repository.SeniorRepository;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MemberRepository memberRepository;
    private final SeniorRepository seniorRepository;
    private final MatchingRepository matchingRepository;

    // 매칭 요청
    @Transactional(rollbackOn = Exception.class)
    public void matchingRequest(String workerId, long seniorId) {
        Member member = memberRepository.findById(workerId).orElse(null);
        Senior senior = seniorRepository.findById(seniorId).orElse(null);

        Matching matching = Matching.builder()
                .senior(senior)
                .member(member)
                .status(MatchingStatus.PENDING)
                .build();

        System.out.println("요청할 어르신 ID: " + matching.getSenior().getId());
        System.out.println("요청 받을 요양보호사 ID: " + matching.getMember().getId());
        System.out.println("매칭 상태: " + matching.getStatus());

        matchingRepository.save(matching);
    }

    // 매칭 상태 수정
    @Transactional(rollbackOn = Exception.class)
    public void matchingStatusPatch(PatchStatusRequest statusRequest) {
        Matching matching = matchingRepository.findBySenior_IdAndMember_Id(Long.parseLong(statusRequest.seniorId()), statusRequest.workerId());

        Matching matchingUpdateObject = Matching.builder()
                .id(matching.getId())
                .senior(matching.getSenior())
                .member(matching.getMember())
                .status(statusRequest.status())
                .build();

        matchingRepository.save(matchingUpdateObject);
    }

}
