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
import young.blaybus.domain.matching.controller.response.*;
import young.blaybus.domain.matching.service.MatchingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
@Tag(name = "매칭 현황 관련 API")
public class MatchingController {

    private final MatchingService matchingService;

    // 매칭 요청 POST
    @PostMapping(value = "/request/{worker-id}/{senior-id}")
    @Operation(summary = "(관리자) 매칭 요청")
    public ApiResponse<?> request(
        @PathVariable("senior-id") String seniorId,
        @PathVariable("worker-id") String workerId
    ) {
        matchingService.matchingRequest(workerId, Long.parseLong(seniorId));
        return ApiResponse.onSuccess();
    }

    // 매칭 현황 조회
    @GetMapping(value = "/statistics")
    @Operation(summary = "(요양보호사) 매칭 현황 조회")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetMatchingStatistics.class))
            )
    })
    public ApiResponse<?> get() {
        return ApiResponse.onSuccess(matchingService.getMatching());
    }

    // 관리자 쪽에서 요양보호사 매칭 현황 조회
    @GetMapping(value = "/request/senior/list")
    @Operation(summary = "(요양보호사) 어르신 매칭 요청 목록 조회")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetMatchingSeniorListResponse.class))
            )
    })
    public ApiResponse<?> getMatchingSeniorList() {
        return ApiResponse.onSuccess(matchingService.getMatchingSeniorList());
    }

    // 관리자 쪽에서 매칭중인 어르신 목록 조회
    @GetMapping(value = "/senior/list")
    @Operation(summary = "(관리자) 매칭중인 어르신 목록 조회")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetMatchingSeniorsList.class))
            )
    })
    public ApiResponse<GetMatchingSeniorsList> getMatchingSeniors() {
        return ApiResponse.onSuccess(matchingService.getMatchingSeniors());
    }

    // 수락/거절/조율요청에 따라 Matching 테이블 내 status 컬럼 수정
    @PatchMapping(value = "/status/update")
    @Operation(summary = "(요양보호사) 매칭 상태 수정")
    public ApiResponse<?> update(@RequestBody PatchStatusRequest statusRequest) {
        matchingService.matchingStatusPatch(statusRequest);
        return ApiResponse.onSuccess();
    }

}
