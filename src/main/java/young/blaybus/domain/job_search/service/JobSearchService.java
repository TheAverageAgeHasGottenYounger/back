package young.blaybus.domain.job_search.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.blaybus.domain.job_search.JobSearch;
import young.blaybus.domain.job_search.JobSearchArea;
import young.blaybus.domain.job_search.JobSearchDay;
import young.blaybus.domain.job_search.repository.JobSearchRepository;
import young.blaybus.domain.job_search.request.JobSearchRequest;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobSearchService {
  private final JobSearchRepository jobSearchRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public void createJobSearch(JobSearchRequest jobSearchRequest) {
    Member member = memberRepository.findById(jobSearchRequest.memberId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    JobSearch jobSearch = JobSearch.builder()
            .member(member)
            .startTime(LocalTime.parse(jobSearchRequest.startTime()))
            .endTime(LocalTime.parse(jobSearchRequest.endTime()))
            .salary(jobSearchRequest.salary())
            .createdTime(LocalDateTime.now())
            .build();

    List<JobSearchArea> jobSearchAreas = jobSearchRequest.jobSearchAreas().stream()
            .map(areaRequest -> JobSearchArea.builder()
                    .city(areaRequest.city())
                    .guGun(areaRequest.guGun())
                    .dong(areaRequest.dong())
                    .jobSearch(jobSearch)
                    .build())
            .toList();
    jobSearch.getJobSearchAreas().addAll(jobSearchAreas);


    List<JobSearchDay> jobSearchDays = jobSearchRequest.dayList().stream()
            .map(day -> JobSearchDay.builder()
                    .day(day)
                    .jobSearch(jobSearch)
                    .build())
            .toList();
    jobSearch.getDayList().addAll(jobSearchDays);

    jobSearchRepository.save(jobSearch);
  }
}
