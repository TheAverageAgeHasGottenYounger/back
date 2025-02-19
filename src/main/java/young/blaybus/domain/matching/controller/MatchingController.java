package young.blaybus.domain.matching.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.matching.controller.request.PatchStatusRequest;
import young.blaybus.domain.matching.controller.response.*;
import young.blaybus.domain.matching.service.MatchingService;
import young.blaybus.domain.senior.controller.response.DetailMatchingSeniorResponse;
import young.blaybus.domain.senior.service.SeniorService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
@Tag(name = "매칭 현황 관련 API")
public class MatchingController {

    private final MatchingService matchingService;
    private final SeniorService seniorService;

    // 매칭 요청
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
    public ApiResponse<List<GetMatchingSeniorListResponse>> getMatchingSeniorList() {
        return ApiResponse.onSuccess(matchingService.getMatchingSeniorList());
    }

    // 관리자 쪽에서 매칭중인 어르신 목록 조회
    @GetMapping(value = "/senior/list")
    @Operation(summary = "(관리자) 매칭중인 어르신 목록 조회")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = GetProgressMatchingSeniorList.class))
            )
    })
    public ApiResponse<GetProgressMatchingSeniorList> getMatchingSeniors() {
        return ApiResponse.onSuccess(matchingService.getMatchingSeniors());
    }

    // Matching 테이블 내 status 컬럼 수정
    @PatchMapping(value = "/status/update")
    @Operation(summary = "(요양보호사) 매칭 상태 수정")
    public ApiResponse<?> update(@RequestBody PatchStatusRequest statusRequest) {
        matchingService.matchingStatusPatch(statusRequest);
        return ApiResponse.onSuccess();
    }


    @GetMapping("/{senior-id}")
    @Operation(summary = "매칭 요청 어르신 상세 조회")
    public ApiResponse<DetailMatchingSeniorResponse> getMatchingSenior(
      @PathVariable("senior-id") Long seniorId
    ) {
        return ApiResponse.onSuccess(seniorService.getMatchingSenior(seniorId));
    }

}
