package young.blaybus.domain.job_search.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.job_search.request.CreateJobSearchRequest;
import young.blaybus.domain.job_search.request.UpdateJobSearchRequest;
import young.blaybus.domain.job_search.response.DetailJobSearchResponse;
import young.blaybus.domain.job_search.service.JobSearchService;

@RestController
@RequestMapping("/job-search")
@RequiredArgsConstructor
@Tag(name = "구직 정보 관련 API")
public class JobSearchController {
  private final JobSearchService jobSearchService;

  @PostMapping
  @Operation(summary = "구직 정보 입력")
  public ApiResponse<?> createJobSearch(@RequestBody @Valid CreateJobSearchRequest request) {
    jobSearchService.createJobSearch(request);
    return ApiResponse.onSuccess();
  }

  @GetMapping("/{member-id}")
  @Operation(summary = "회원 ID로 구직 정보 조회")
  public ApiResponse<DetailJobSearchResponse> getJobSearch(@PathVariable("member-id") String memberId) {
    return ApiResponse.onSuccess(jobSearchService.getJobSearch(memberId));
  }

  @PatchMapping("/{job-search-id}")
  @Operation(summary = "구직 정보 수정")
  public ApiResponse<?> updateJobSearch(
          @PathVariable("job-search-id") Long jobSearchId,
          @RequestBody @Valid UpdateJobSearchRequest request) {
    jobSearchService.updateJobSearch(jobSearchId, request);
    return ApiResponse.onSuccess();
  }
}
