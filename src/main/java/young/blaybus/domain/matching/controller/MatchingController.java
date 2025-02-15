package young.blaybus.domain.matching.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.matching.controller.request.PatchStatusRequest;
import young.blaybus.domain.matching.service.MatchingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
@Tag(name = "매칭 관리 관련 API")
public class MatchingController {

    private final MatchingService matchingService;

    // 매칭 요청 POST
    @PostMapping(value = "/request/{senior-id}/{worker-id}")
    public ApiResponse<?> request(
        @PathVariable("senior-id") String seniorId,
        @PathVariable("worker-id") String workerId
    ) {
        matchingService.matchingRequest(workerId, Long.parseLong(seniorId));
        return ApiResponse.onSuccess();
    }

    // 수락/거절/조율요청에 따라 Matching 테이블 내 status 컬럼 갱신
    @PatchMapping(value = "/status/update")
    public ApiResponse<?> correction(@RequestBody PatchStatusRequest statusRequest) {
        matchingService.matchingStatusPatch(statusRequest);
        return ApiResponse.onSuccess();
    }

}
