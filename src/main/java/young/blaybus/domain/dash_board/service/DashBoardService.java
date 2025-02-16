package young.blaybus.domain.dash_board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import young.blaybus.domain.dash_board.controller.response.GetMatchingStatisticsResponse;
import young.blaybus.domain.dash_board.controller.response.GetProgressionMatchingResponse;
import young.blaybus.domain.matching.Matching;
import young.blaybus.domain.matching.repository.MatchingRepository;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.member.security.SecurityUtils;
import young.blaybus.domain.senior.service.ListSeniorService;
import young.blaybus.util.enums.DayOfWeek;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class DashBoardService {

    private final MemberRepository memberRepository;
    private final MatchingRepository matchingRepository;
    private final ListSeniorService listSeniorService;

    // (전체, 신규) 매칭 통계 조회
    @Transactional(rollbackOn = Exception.class)
    public GetMatchingStatisticsResponse matchingStatistics() {
        String adminId = SecurityUtils.getCurrentMemberName();

        // 해당 ID로 센터 검색
        Member member = memberRepository.findById(adminId).orElse(null);
        if (member != null && member.getCenter() != null) {
            // 해당 센터 ID에 부합하는 어르신 리스트 얻기
            AtomicInteger fullMatchingCount = new AtomicInteger();
            AtomicInteger newMatchingCount = new AtomicInteger();
            listSeniorService.getSeniorList().getSeniorList().forEach(seniorList -> {
                List<Matching> matching = matchingRepository.findBySenior_Id(seniorList.getSeniorId());
                matching.forEach(m -> fullMatchingCount.incrementAndGet());

                // 요일 기준으로 신규 매칭 현황 조회
                matching.forEach(m -> {
                    String[] createTime = String.valueOf(m.getCreatedTime()).split(" ");
                    String[] nowTime = String.valueOf(LocalDateTime.now()).split(" ");

                    if (createTime[0].substring(8, createTime[0].indexOf("T")).equals(nowTime[0].substring(8, nowTime[0].indexOf("T"))))
                        newMatchingCount.incrementAndGet();
                });
            });

            return GetMatchingStatisticsResponse.builder()
                    .fullMatching(fullMatchingCount.get())
                    .newMatching(newMatchingCount.get())
                    .build();
        }

        return null;
    }

    // 진행중인 매칭 조회
    @Transactional(rollbackOn = Exception.class)
    public List<GetProgressionMatchingResponse> progressionMatching() {
        String adminId = SecurityUtils.getCurrentMemberName();

        List<GetProgressionMatchingResponse> response = new ArrayList<>();

        Member member = memberRepository.findById(adminId).orElse(null);
        if (member != null && member.getCenter() != null) {
            listSeniorService.getSeniorList().getSeniorList().forEach(seniorList -> {
                AtomicInteger atmosphere = new AtomicInteger();
                AtomicInteger acceptance = new AtomicInteger();
                AtomicInteger refusal = new AtomicInteger();
                AtomicInteger tuning = new AtomicInteger();

                List<Matching> matching = matchingRepository.findBySenior_Id(seniorList.getSeniorId());
                matching.forEach(m -> {
                    switch (m.getStatus()) {
                        case PENDING -> atmosphere.getAndIncrement();
                        case ACCEPTED -> acceptance.getAndIncrement();
                        case REJECTED -> refusal.getAndIncrement();
                        case TUNE_REQUESTED -> tuning.getAndIncrement();
                    }

                    List<DayOfWeek> seniorDays = new  ArrayList<>();
                    m.getSenior().getDayList().forEach(day -> seniorDays.add(day.getDay()));

                    GetProgressionMatchingResponse progressionMatching = GetProgressionMatchingResponse.builder()
                            .profileImageUrl(m.getSenior().getProfileUrl())
                            .seniorName(m.getSenior().getName())
                            .seniorDay(seniorDays)
                            .startTime(m.getSenior().getStartTime().format(DateTimeFormatter.ofPattern("a HH:mm").withLocale(Locale.forLanguageTag("ko"))))
                            .atmosphere(atmosphere.get())
                            .acceptance(acceptance.get())
                            .refusal(refusal.get())
                            .tuning(tuning.get())
                            .build();

                    if (response.isEmpty()) response.add(progressionMatching);

                    AtomicBoolean isDuplication = new AtomicBoolean(false);
                    response.forEach(r -> {
                        if (m.getSenior().getName().contains(r.getSeniorName())) {
                            r.setSeniorDay(seniorDays);
                            r.setAtmosphere(atmosphere.get());
                            r.setAcceptance(acceptance.get());
                            r.setRefusal(refusal.get());
                            r.setTuning(tuning.get());
                            isDuplication.set(false);
                        } else isDuplication.set(true);
                    });

                    if (isDuplication.get()) response.add(progressionMatching);
                });
            });

            return response;
        }

        return null;
    }

}
