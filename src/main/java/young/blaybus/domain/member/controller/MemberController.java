package young.blaybus.domain.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.member.request.MemberRequest;
import young.blaybus.domain.member.service.MemberService;

@Controller
@RequestMapping("/api/v3/member")
@RequiredArgsConstructor
@Tag(name = "회원 관련 API")
public class MemberController {

    private final MemberService memberService;

    // 스탭별로 회원가입 진행 (제일 끝 단계는 회원가입을 진행 -> DB에 저장)
    @PostMapping("/join")
    @ResponseBody
    public ApiResponse<?> workerJoinThirdStep(@RequestBody MemberRequest memberRequest) {
        memberService.registerMember(memberRequest);
        return ApiResponse.onSuccess();
    }

    // 회원 아이디 중복 체크
    @GetMapping("/duplication/id")
    @ResponseBody
    public ApiResponse<?> duplicationIdCheck(@RequestParam String memberId) {
        String duplication = memberService.duplicationIdCheck(memberId);
        if (duplication == null) return ApiResponse.onFailure("409", "아이디가 중복됩니다.", "");
        return ApiResponse.onSuccess();
    }

    // 회원 이름 중복 체크
    @GetMapping("/duplication/name")
    @ResponseBody
    public ApiResponse<?> duplicationNameCheck(@RequestParam String memberName) {
        String duplication = memberService.duplicationNameCheck(memberName);
        if (duplication == null) return ApiResponse.onFailure("409", "이름이 중복됩니다.", "");
        return ApiResponse.onSuccess();
    }

}
