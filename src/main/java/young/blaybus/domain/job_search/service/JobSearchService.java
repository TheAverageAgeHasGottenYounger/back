package young.blaybus.domain.job_search.service;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.blaybus.api_response.exception.GeneralException;
import young.blaybus.api_response.status.ErrorStatus;
import young.blaybus.domain.certificate.Certificate;
import young.blaybus.domain.certificate.controller.response.ListCertificateDto;
import young.blaybus.domain.certificate.repository.CertificateRepository;
import young.blaybus.domain.job_search.JobSearch;
import young.blaybus.domain.job_search.JobSearchArea;
import young.blaybus.domain.job_search.JobSearchDay;
import young.blaybus.domain.job_search.repository.JobSearchRepository;
import young.blaybus.domain.job_search.request.CreateJobSearchRequest;
import young.blaybus.domain.job_search.request.UpdateJobSearchRequest;
import young.blaybus.domain.job_search.response.DetailJobSearchResponse;
import young.blaybus.domain.job_search.response.JobSearchAreaResponse;
import young.blaybus.domain.member.Member;
import young.blaybus.domain.member.repository.MemberRepository;
import young.blaybus.domain.senior.Senior;
import young.blaybus.domain.senior.repository.SeniorRepository;
import young.blaybus.domain.senior.service.RecommendService;
import young.blaybus.util.enums.DayOfWeek;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobSearchService {

  private final JobSearchRepository jobSearchRepository;
  private final MemberRepository memberRepository;
  private final CertificateRepository certificateRepository;
  private final SeniorRepository seniorRepository;
  private final RecommendService recommendService;

  @Transactional
  public void createJobSearch(CreateJobSearchRequest jobSearchRequest) {
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
        .address(areaRequest.address())
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

  @Transactional
  public void updateJobSearch(Long jobSearchId, UpdateJobSearchRequest request) {
    JobSearch jobSearch = jobSearchRepository.findById(jobSearchId)
      .orElseThrow(() -> new IllegalArgumentException("구직 정보가 존재하지 않습니다."));

    jobSearch.updateFromDto(request);

    jobSearchRepository.save(jobSearch);
  }

  @Transactional
  public DetailJobSearchResponse getJobSearch(String memberId, Long seniorId) {
    Member member = memberRepository.findById(memberId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND, "존재하지 않는 회원 ID입니다."));
    Senior senior = seniorRepository.findById(seniorId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND, "존재하지 않는 어르신 ID입니다."));

    JobSearch jobSearch = jobSearchRepository.findByMemberId(memberId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND, "해당 회원의 구직 정보가 존재하지 않습니다."));

    List<JobSearchAreaResponse> jobSearchAreaResponses = jobSearch.getJobSearchAreas().stream().map(jobSearchArea ->
      JobSearchAreaResponse.builder().address(jobSearchArea.getAddress()).build()
    ).toList();

    List<String> dayList = jobSearch.getDayList().stream()
      .map(JobSearchDay::getDay)
      .map(DayOfWeek::toString)
      .toList();

    List<ListCertificateDto> certificateDtoList = new ArrayList<>();
    List<Certificate> certificateList = certificateRepository.findByMember(member).orElse(new ArrayList<>());
    for (Certificate certificate : certificateList) {
      certificateDtoList.add(
        ListCertificateDto.builder()
          .type(certificate.getType().getValue())
          .grade(certificate.getGrade().getValue())
          .number(certificate.getNumber())
          .build()
      );
    }

    return DetailJobSearchResponse.builder()
      .jobSearchId(jobSearch.getId())
      .name(member.getName())
      .startTime(jobSearch.getStartTime())
      .endTime(jobSearch.getEndTime())
      .careStyle(member.getCareStyle())
      .salary(jobSearch.getSalary())
      .jobSearchAreas(jobSearchAreaResponses)
      .dayList(dayList)
      .certificateList(certificateDtoList)
      .career(member.getCareer())
      .careerPeriod(member.getCareerPeriod())
      .introduction(member.getIntroduction())
      .fitness(recommendService.calculateFitness(member, senior))
      .build();
  }
}
