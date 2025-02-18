package young.blaybus.domain.matching.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import young.blaybus.domain.matching.Matching;
import young.blaybus.domain.matching.controller.request.PatchStatusRequest;
import young.blaybus.domain.matching.controller.response.*;
import young.blaybus.domain.matching.enums.MatchingStatus;
import young.blaybus.domain.matching.repository.MatchingRepository;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.member.security.SecurityUtils;
import young.blaybus.domain.senior.Senior;
import young.blaybus.domain.senior.repository.SeniorRepository;
import young.blaybus.util.enums.DayOfWeek;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MemberRepository memberRepository;
    private final SeniorRepository seniorRepository;
    private final MatchingRepository matchingRepository;

    // 매칭 요청
    @Transactional
    public void matchingRequest(String workerId, long seniorId) {
        Member member = memberRepository.findById(workerId).orElse(null);
        Senior senior = seniorRepository.findById(seniorId).orElse(null);

        Matching matching = Matching.builder()
                .senior(senior)
                .member(member)
                .status(MatchingStatus.PENDING)
                .createdTime(LocalDateTime.now())
                .build();

        matchingRepository.save(matching);
    }

    // 매칭 현황 조회 (특정)
    @Transactional
    public GetMatchingStatistics getMatching() {
        String workerId = SecurityUtils.getCurrentMemberName();
        Member member = memberRepository.findById(workerId).orElse(null);
        if (member != null) {
            List<Matching> matching = matchingRepository.findByMember_Id(member.getId());

            AtomicInteger fullMatchingCount = new AtomicInteger();
            AtomicInteger acceptance = new AtomicInteger();
            AtomicInteger refusal = new AtomicInteger();
            AtomicInteger tuning = new AtomicInteger();

            matching.forEach(m -> {
                switch (m.getStatus()) {
                    case ACCEPTED -> acceptance.getAndIncrement();
                    case REJECTED -> refusal.getAndIncrement();
                    case TUNE_REQUESTED -> tuning.getAndIncrement();
                }
                fullMatchingCount.getAndIncrement();
            });

            return GetMatchingStatistics.builder()
                    .fullMatching(fullMatchingCount.get())
                    .acceptance(acceptance.get())
                    .refusal(refusal.get())
                    .tuning(tuning.get())
                    .build();
        }

        return null;
    }

    // 매칭 현황 어르신 리스트 조회 -> 요양보호사 쪽에서 어르신 매칭 요청 조회
    @Transactional
    public List<GetMatchingSeniorListResponse> getMatchingSeniorList() {
        String workerId = SecurityUtils.getCurrentMemberName();
        List<Matching> matching = matchingRepository.findByMember_Id(workerId);

        List<GetMatchingSeniorListResponse> responseList = new ArrayList<>();
        matching.forEach(m -> {
            List<DayOfWeek> days =  new ArrayList<>();
            m.getSenior().getDayList().forEach(day -> {
                days.add(day.getDay());
            });

            responseList.add(
                GetMatchingSeniorListResponse.builder()
                    .seniorId(String.valueOf(m.getSenior().getId()))
                    .profileUrl(m.getSenior().getProfileUrl())
                    .seniorName(m.getSenior().getName())
                    .address(m.getSenior().getAddress())
                    .seniorDay(days)
                    .startTime(m.getSenior().getStartTime().format(DateTimeFormatter.ofPattern("a HH:mm").withLocale(Locale.forLanguageTag("ko"))))
                    .endTime(m.getSenior().getEndTime().format(DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.forLanguageTag("ko"))))
                    .careStyle(m.getSenior().getCareStyle().getValue())
                    .build()
            );
        });

        return responseList;
    }

    // 매칭 상태 수정
    @Transactional
    public void matchingStatusPatch(PatchStatusRequest statusRequest) {
        String workerId = SecurityUtils.getCurrentMemberName();
        Matching matching = matchingRepository.findBySenior_IdAndMember_Id(Long.parseLong(statusRequest.seniorId()), workerId);

        Matching matchingUpdateObject = Matching.builder()
                .id(matching.getId())
                .senior(matching.getSenior())
                .member(matching.getMember())
                .status(statusRequest.status())
                .build();

        matchingRepository.save(matchingUpdateObject);
    }

    // 매칭중인 어르신 목록 조회
    @Transactional
    public GetProgressMatchingSeniorList getMatchingSeniors() {
        String adminId = SecurityUtils.getCurrentMemberName();
        Member member = memberRepository.findById(adminId).orElse(null);

        List<GetProgressMatchingSeniors> seniorsList = new ArrayList<>();
        if (member != null) {
            List<Senior> center = seniorRepository.findByCenterId(member.getCenter().getId());
            center.forEach(s -> {
                List<Matching> matching = matchingRepository.findBySenior_Id(s.getId());
                matching.forEach(m -> {
                    if (m.getStatus().equals(MatchingStatus.PENDING)) {
                        seniorsList.add(
                            GetProgressMatchingSeniors.builder()
                                .seniorId(String.valueOf(s.getId()))
                                .profileUrl(s.getProfileUrl())
                                .name(s.getName())
                                .sex(s.getSex().name())
                                .birthday(s.getBirthday().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                                .address(s.getAddress())
                                .build()
                        );
                    }
                });
            });

        }

        return GetProgressMatchingSeniorList.builder()
                .seniorList(seniorsList)
                .build();
    }

}
