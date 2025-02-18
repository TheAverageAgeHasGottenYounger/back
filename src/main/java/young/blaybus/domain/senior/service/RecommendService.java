package young.blaybus.domain.senior.service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.blaybus.api_response.exception.GeneralException;
import young.blaybus.api_response.status.ErrorStatus;
import young.blaybus.domain.certificate.Certificate;
import young.blaybus.domain.certificate.enums.CertificateType;
import young.blaybus.domain.certificate.repository.ListCertificateRepository;
import young.blaybus.domain.job_search.JobSearch;
import young.blaybus.domain.job_search.JobSearchDay;
import young.blaybus.domain.job_search.JobSearchTimeSlot;
import young.blaybus.domain.job_search.repository.JobSearchRepository;
import young.blaybus.domain.job_seek.JobSeek;
import young.blaybus.domain.job_seek.repository.JobSeekRepository;
import young.blaybus.domain.matching.repository.ListMatchingRepository;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.senior.Senior;
import young.blaybus.domain.senior.SeniorDay;
import young.blaybus.domain.senior.controller.response.ListRecommendDto;
import young.blaybus.domain.senior.controller.response.ListRecommendResponse;
import young.blaybus.domain.senior.controller.response.TimeSlotDto;
import young.blaybus.domain.senior.repository.ListRecommendRepository;
import young.blaybus.domain.senior.repository.SeniorRepository;
import young.blaybus.map.controller.response.geocoding.Coordinate;
import young.blaybus.map.service.MapService;
import young.blaybus.util.enums.DayOfWeek;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendService {

  private final ListRecommendRepository listRecommendRepository;
  private final SeniorRepository seniorRepository;
  private final JobSearchRepository jobSearchRepository;
  private final JobSeekRepository jobSeekRepository;
  private final ListMatchingRepository listMatchingRepository;
  private final ListCertificateRepository listCertificateRepository;
  private final MapService mapService;

  public ListRecommendResponse getRecommendList(Long seniorId) {
    Senior senior = seniorRepository.findById(seniorId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST));

    List<Member> memberList = listRecommendRepository.getRecommendList(senior);
    List<ListRecommendDto> recommendList = new ArrayList<>();
    for (Member member : memberList) {

      JobSearch jobSearch = jobSearchRepository.findByMemberId(member.getId()).orElse(null);
      if (jobSearch == null) continue;

      // timeSlots 기반으로 요일 및 시간대 가져오기
      List<TimeSlotDto> timeSlotDtos = jobSearch.getTimeSlots().stream()
              .map(slot -> TimeSlotDto.builder()
                      .day(slot.getDay())
                      .startTime(slot.getStartTime().toString()) // LocalTime → String 변환
                      .endTime(slot.getEndTime().toString())
                      .build())
              .toList();

      recommendList.add(
        ListRecommendDto.builder()
          .memberId(member.getId())
          .name(member.getName())
          .profileUrl(member.getProfileUrl())
          .timeSlots(timeSlotDtos)
          .careStyle(member.getCareStyle().getValue())
          .fitness(calculateFitness(member, jobSearch, senior))
          .build()
      );
    }

    // 적합도 기준 내림차순 정렬
    recommendList.sort(Comparator.comparingDouble(ListRecommendDto::getFitness).reversed());
    return ListRecommendResponse.builder()
      .recommendList(recommendList)
      .build();
  }

  private int calculateFitness(Member member, JobSearch jobSearch, Senior senior) {
    double fitness = 0.0;

    JobSeek jobSeek = jobSeekRepository.findBySenior(senior);

    // 거리 : 0km 최고점, 350km 최하점 → 30점 만점
    fitness += 30;
    Coordinate memberGeocoding = mapService.geocoding(member.getAddress().toString());
    Coordinate seniorGeocoding = mapService.geocoding(senior.getAddress());
    Double distance = mapService.getDistance(memberGeocoding, seniorGeocoding);
    double maxDistance = 350_000;

    fitness -= Math.min(30, distance * 30 / maxDistance);

    // 요일 → (노인의 희망 요일이 보호사의 요일과 겹치는 개수) * 15 / (노인의 희망 요일 개수) 점 → 15점 만점
    List<DayOfWeek> memberDayList = jobSearch.getTimeSlots().stream().map(JobSearchTimeSlot::getDay).toList();
    List<DayOfWeek> seniorDayList = senior.getDayList().stream().map(SeniorDay::getDay).toList();
    int intersectCount = memberDayList.stream()
      .filter(seniorDayList::contains)
      .collect(Collectors.toSet()).size();
    fitness += (double) (15 * intersectCount) / seniorDayList.size();

    // 시간 → 겹치는 시간의 비율 * 15 (15 만점)
    for (JobSearchTimeSlot slot : jobSearch.getTimeSlots()) {
      LocalTime memberStart = slot.getStartTime();
      LocalTime memberEnd = slot.getEndTime();
      LocalTime seniorStart = senior.getStartTime();
      LocalTime seniorEnd = senior.getEndTime();

      LocalTime overlapStart = memberStart.isBefore(seniorStart) ? seniorStart : memberStart;
      LocalTime overlapEnd = memberEnd.isBefore(seniorEnd) ? memberEnd : seniorEnd;

      if (overlapStart.isBefore(overlapEnd)) { // 겹치는 경우
        long overlapMinutes = Duration.between(overlapStart, overlapEnd).toMinutes();
        long memberTotalMinutes = Duration.between(memberStart, memberEnd).toMinutes();
        if (memberTotalMinutes > 0) {
          fitness += (double) 15 * overlapMinutes / memberTotalMinutes;
        }
      }
    }

    // 자격증 : 요양보호사 제외 개수당 3점 → 10점 만점, 사회복지사 자격증 번호 1로 시작(1급)하면 2점 추가 (10 만점)
    List<Certificate> certificateList = listCertificateRepository.getCertificateList(member);
    fitness += certificateList.size();
    for (Certificate certificate : certificateList) {
      if (certificate.getType().equals(CertificateType.SOCIAL)
        && certificate.getNumber().startsWith("1")) fitness += 2;
    }

    // 노인 시급 < 보호사 시급일 때, 10 - 0.001 * (보호사 시급 - 노인 시급) → 1000원 차이당 1점 감소, 최소 0점 (10 만점)
    fitness += 10;
    if (jobSeek.getSalary() < jobSearch.getSalary())
      fitness -= Math.max(10, 0.001 * (jobSearch.getSalary() - jobSeek.getSalary()));

    // 요양 스타일 일치하면 5점
    if (member.getCareStyle().equals(senior.getCareStyle())) fitness += 5;

    // 매칭 횟수 → 5점 만점, 매칭 횟수 / 2 점
    long count = listMatchingRepository.countAccepted(member);
    fitness += Math.max(5, count / 2);

    // 치매 교육 여부  → 5점, 차량 소유 여부  → 5점
    if (member.getCarYn()) fitness += 5;
    if (member.getDementiaEducationYn()) fitness += 5;

    return (int) fitness;
  }
}
