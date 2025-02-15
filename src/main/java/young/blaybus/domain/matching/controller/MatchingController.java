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
@Tag(name = "매칭 현황 관련 API")
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

    // 매칭 현황 조회
    @GetMapping(value = "/request/get/{senior-id}/{worker-id}")
    public ApiResponse<?> get(@PathVariable("senior-id") String seniorId, @PathVariable("worker-id") String workerId) {
        return ApiResponse.onSuccess(matchingService.getMatching(workerId, seniorId));
    }

    // 매칭 현황 요양보호사 리스트 조회 -> 관리자 쪽에서 요양보호사 매칭 현황 조회
    @GetMapping(value = "/list/worker/{worker-id}")
    public ApiResponse<?> getMatchingSeniorList(@PathVariable("worker-id") String workerId) {
        return ApiResponse.onSuccess(matchingService.getMatchingSeniorList(workerId));
    }

    // 매칭 현황 어르신 리스트 조회 -> 요양보호사 쪽에서 어르신 매칭 현황 조회
    @GetMapping(value = "/list/senior/{senior-id}")
    public ApiResponse<?> getMatchingWorkerList(@PathVariable("senior-id") String seniorId) {
        return ApiResponse.onSuccess(matchingService.getMatchingWorkerList(seniorId));
    }

    // 수락/거절/조율요청에 따라 Matching 테이블 내 status 컬럼 갱신
    @PatchMapping(value = "/status/update")
    public ApiResponse<?> update(@RequestBody PatchStatusRequest statusRequest) {
        matchingService.matchingStatusPatch(statusRequest);
        return ApiResponse.onSuccess();
    }

}
