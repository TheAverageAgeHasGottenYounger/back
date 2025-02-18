package young.blaybus.domain.job_search.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.blaybus.domain.job_search.JobSearch;
import young.blaybus.domain.job_search.JobSearchArea;
import young.blaybus.domain.job_search.JobSearchTimeSlot;
import young.blaybus.domain.job_search.repository.JobSearchRepository;
import young.blaybus.domain.job_search.request.CreateJobSearchRequest;
import young.blaybus.domain.job_search.request.UpdateJobSearchRequest;
import young.blaybus.domain.job_search.response.DetailJobSearchResponse;
import young.blaybus.domain.job_search.response.JobSearchAreaResponse;
import young.blaybus.domain.job_search.response.JobSearchTimeSlotResponse;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobSearchService {
  private final JobSearchRepository jobSearchRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public void createJobSearch(CreateJobSearchRequest jobSearchRequest) {
    Member member = memberRepository.findById(jobSearchRequest.memberId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    JobSearch jobSearch = JobSearch.builder()
            .member(member)
            .salary(jobSearchRequest.salary())
            .createdTime(LocalDateTime.now())
            .build();

    List<JobSearchArea> areas = jobSearchRequest.jobSearchAreas().stream()
            .map(areaRequest -> JobSearchArea.builder()
                    .address(areaRequest.address())
                    .jobSearch(jobSearch)
                    .build())
            .toList();
    jobSearch.getJobSearchAreas().addAll(areas);


    List<JobSearchTimeSlot> timeSlots = jobSearchRequest.timeSlots().stream()
            .map(slot -> JobSearchTimeSlot.builder()
                    .jobSearch(jobSearch)
                    .day(slot.day())
                    .startTime(LocalTime.parse(slot.startTime()))
                    .endTime(LocalTime.parse(slot.endTime()))
                    .build())
            .toList();
    jobSearch.getTimeSlots().addAll(timeSlots);

    jobSearchRepository.save(jobSearch);
  }

  @Transactional
  public void updateJobSearch(Long jobSearchId, UpdateJobSearchRequest request) {
    JobSearch jobSearch=jobSearchRepository.findById(jobSearchId)
            .orElseThrow(() -> new IllegalArgumentException("구직 정보가 존재하지 않습니다."));

    jobSearch.updateFromDto(request);

    jobSearchRepository.save(jobSearch);
  }

  @Transactional
  public DetailJobSearchResponse getJobSearch(String memberId) {
    JobSearch jobSearch= jobSearchRepository.findByMemberId(memberId)
            .orElseThrow(()->new IllegalArgumentException("해당 회원의 구직 정보가 존재하지 않습니다."));

    List<JobSearchAreaResponse> areaResponses = jobSearch.getJobSearchAreas().stream().map(jobSearchArea ->
      JobSearchAreaResponse.builder().address(jobSearchArea.getAddress()).build()
    ).toList();

    List<JobSearchTimeSlotResponse> timeSlotResponses  = jobSearch.getTimeSlots().stream()
            .map(slot -> JobSearchTimeSlotResponse.builder()
                    .day(slot.getDay())
                    .startTime(slot.getStartTime().toString())
                    .endTime(slot.getEndTime().toString())
                    .build())
            .toList();

    return DetailJobSearchResponse.builder()
            .jobSearchId(jobSearch.getId())
            .salary(jobSearch.getSalary())
            .jobSearchAreas(areaResponses)
            .timeSlots(timeSlotResponses)
            .build();

  }
}
