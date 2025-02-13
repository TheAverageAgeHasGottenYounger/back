package young.blaybus.domain.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.center.service.CenterService;
import young.blaybus.domain.member.request.AdminRequest;
import young.blaybus.domain.member.request.MemberRequest;
import young.blaybus.domain.member.service.MemberService;

@Controller
@RequestMapping("/api/v3/member")
@RequiredArgsConstructor
@Tag(name = "회원 관련 API")
public class MemberController {

    private final MemberService memberService;
    private final CenterService centerService;

    @PostMapping("/worker/join")
    @ResponseBody
    public ApiResponse<?> workerJoin(@RequestBody MemberRequest memberRequest) {
        memberService.workerRegisterMember(memberRequest);
        return ApiResponse.onSuccess();
    }

    @PostMapping("/admin/join")
    @ResponseBody
    public ApiResponse<?> adminJoin(@RequestBody AdminRequest adminRequest) {
        memberService.adminRegisterMember(adminRequest);
        centerService.registerCenter(adminRequest.center(), adminRequest);
        return ApiResponse.onSuccess();
    }

    // 회원 아이디 중복 체크
    @GetMapping("/duplication-id")
    @ResponseBody
    public ApiResponse<?> duplicationIdCheck(@RequestParam String memberId) {
        String duplication = memberService.duplicationIdCheck(memberId);
        if (duplication == null) return ApiResponse.onFailure("409", "아이디가 중복됩니다.", "");
        return ApiResponse.onSuccess();
    }

    // 회원 이름 중복 체크
    @GetMapping("/duplication-name")
    @ResponseBody
    public ApiResponse<?> duplicationNameCheck(@RequestParam String memberName) {
        String duplication = memberService.duplicationNameCheck(memberName);
        if (duplication == null) return ApiResponse.onFailure("409", "이름이 중복됩니다.", "");
        return ApiResponse.onSuccess();
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiResponse<?> login(@RequestParam String id, @RequestParam String password) {
        return ApiResponse.onSuccess();
    }

}
