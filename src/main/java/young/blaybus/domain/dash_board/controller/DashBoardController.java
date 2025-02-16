package young.blaybus.domain.dash_board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.dash_board.controller.response.GetMatchingStatisticsResponse;
import young.blaybus.domain.dash_board.controller.response.GetProgressionMatchingResponse;
import young.blaybus.domain.dash_board.service.DashBoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/dashboard")
@Tag(name = "대시보드 관련 API")
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @GetMapping(value = "/matching/statistics")
    @Operation(summary = "매칭 통계 조회")
    public ApiResponse<GetMatchingStatisticsResponse> fullMatchingStatistics() {
        GetMatchingStatisticsResponse result = dashBoardService.matchingStatistics();
        if (result == null) return ApiResponse.onFailure("400", "관리자만 대시보드 API에 접근할 수 있습니다.", null);
        return ApiResponse.onSuccess(result);
    }

    @GetMapping(value = "/progression/matching")
    @Operation(summary = "진행중인 매칭 조회")
    public ApiResponse<List<GetProgressionMatchingResponse>> progressionMatching() {
        List<GetProgressionMatchingResponse> result = dashBoardService.progressionMatching();
        if (result == null) return ApiResponse.onFailure("400", "관리자만 대시보드 API에 접근할 수 있습니다.", null);
        return ApiResponse.onSuccess(result);
    }

}
