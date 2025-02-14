package young.blaybus.domain.job_search.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.job_search.request.JobSearchRequest;
import young.blaybus.domain.job_search.service.JobSearchService;

@RestController
@RequestMapping("/job-search")
@RequiredArgsConstructor
@Tag(name = "구직 정보 관련 API")
public class JobSearchController {
  private final JobSearchService jobSearchService;

  @PostMapping
  @Operation(summary = "구직 정보 입력")
  public ApiResponse<?> createJobSearch(@RequestBody @Valid JobSearchRequest request) {
    jobSearchService.createJobSearch(request);
    return ApiResponse.onSuccess();
  }

}
