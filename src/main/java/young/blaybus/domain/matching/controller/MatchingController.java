package young.blaybus.domain.matching.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.matching.service.MatchingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
@Tag(name = "매칭 관리 관련 API")
public class MatchingController {

    private final MatchingService matchingService;

    /*
     * 매칭 현황 프로세스 이해
     * 1. 관리자가 요양보호사에게 요청을 보낸다. -> 그럼 관리자 ID와 매칭 요청한 요양보호사 ID를 얻고 DB에 저장한다. 아, 상태는 수락 대기 상태로 저장한다.
     * 2. 그럼 요양보호사에게 매칭 요청 리스트를 보여준다. 상세보기를 누르면 수락, 거절, 조율 요청 상태가 있는데 하나만 선택해서 -> DB에 상태 컬럼에 업데이트 해준다.
     *    (근무 조건 테이블이 있는데 그건 어르신 ID를 가지고 얻으면 되니까)
     */

    // 매칭 요청 POST
    @PostMapping(value = "/request/{senior-id}/{worker-id}")
    public ApiResponse<?> request(
        @PathVariable("senior-id") String seniorId,
        @PathVariable("worker-id") String workerId
    ) {
        matchingService.matchingRequest(workerId, Long.parseLong(seniorId));
        return ApiResponse.onSuccess();
    }

    // 어르신 (근무 조건 포함) 리스트 조회 GET


}
