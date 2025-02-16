package young.blaybus.domain.matching.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.matching.controller.request.PatchStatusRequest;
import young.blaybus.domain.matching.controller.response.GetMatching;
import young.blaybus.domain.matching.controller.response.GetMatchingSeniorList;
import young.blaybus.domain.matching.controller.response.GetMatchingWorkerList;
import young.blaybus.domain.matching.service.MatchingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
@Tag(name = "매칭 현황 관련 API")
public class MatchingController {

    private final MatchingService matchingService;

    // 매칭 요청 POST
    @PostMapping(value = "/request/{senior-id}/{worker-id}")
    @Operation(summary = "매칭 요청")
    public ApiResponse<?> request(
        @PathVariable("senior-id") String seniorId,
        @PathVariable("worker-id") String workerId
    ) {
        matchingService.matchingRequest(workerId, Long.parseLong(seniorId));
        return ApiResponse.onSuccess();
    }

    // 매칭 현황 조회
    @GetMapping(value = "/request/get/{senior-id}/{worker-id}")
    @Operation(summary = "매칭 현황 조회")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = GetMatching.class))
        )
    })
    public ApiResponse<?> get(@PathVariable("senior-id") String seniorId, @PathVariable("worker-id") String workerId) {
        return ApiResponse.onSuccess(matchingService.getMatching(workerId, seniorId));
    }

    // 관리자 쪽에서 요양보호사 매칭 현황 조회
    @GetMapping(value = "/list/senior/{worker-id}")
    @Operation(summary = "매칭 현황 어르신 리스트 조회")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetMatchingSeniorList.class))
            )
    })
    public ApiResponse<?> getMatchingSeniorList(@PathVariable("worker-id") String workerId) {
        return ApiResponse.onSuccess(matchingService.getMatchingSeniorList(workerId));
    }

    // 요양보호사 쪽에서 어르신 매칭 현황 조회
    @GetMapping(value = "/list/worker/{senior-id}")
    @Operation(summary = "매칭 현황 요양보호사 리스트 조회")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetMatchingWorkerList.class))
            )
    })
    public ApiResponse<?> getMatchingWorkerList(@PathVariable("senior-id") String seniorId) {
        return ApiResponse.onSuccess(matchingService.getMatchingWorkerList(seniorId));
    }

    // 수락/거절/조율요청에 따라 Matching 테이블 내 status 컬럼 수정
    @PatchMapping(value = "/status/update")
    @Operation(summary = "매칭 상태 수정")
    public ApiResponse<?> update(@RequestBody PatchStatusRequest statusRequest) {
        matchingService.matchingStatusPatch(statusRequest);
        return ApiResponse.onSuccess();
    }

}
