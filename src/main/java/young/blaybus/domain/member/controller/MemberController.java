package young.blaybus.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.center.service.CenterService;
import young.blaybus.domain.member.request.CreateAdminRequest;
import young.blaybus.domain.member.request.CreateMemberRequest;
import young.blaybus.domain.member.service.MemberService;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "회원 관련 API")
public class MemberController {

    private final MemberService memberService;
    private final CenterService centerService;

    // 요양보호사 회원가입
    @PostMapping("/worker-join")
    @Operation(summary = "요양보호사 회원가입")
    public ApiResponse<?> workerJoin(@RequestBody CreateMemberRequest memberRequest) {
        memberService.workerRegisterMember(memberRequest);
        return ApiResponse.onSuccess();
    }

    // 관리자 회원가입
    @PostMapping("/admin-join")
    @Operation(summary = "관리자 회원가입")
    public ApiResponse<?> adminJoin(@RequestBody CreateAdminRequest adminRequest) {
        memberService.adminRegisterMember(adminRequest);
        centerService.registerCenter(adminRequest.center(), adminRequest);
        return ApiResponse.onSuccess();
    }

    // 회원 아이디 중복 체크
    @GetMapping("/duplication-id")
    @Operation(summary = "회원 아이디 중복 체크")
    public ApiResponse<?> duplicationIdCheck(@RequestParam String memberId) {
        String duplication = memberService.duplicationIdCheck(memberId);
        if (duplication == null) return ApiResponse.onFailure("409", "아이디가 중복됩니다.", "");
        return ApiResponse.onSuccess();
    }

    // 회원 이름 중복 체크
    @GetMapping("/duplication-name")
    @Operation(summary = "회원 이름 중복 체크")
    public ApiResponse<?> duplicationNameCheck(@RequestParam String memberName) {
        String duplication = memberService.duplicationNameCheck(memberName);
        if (duplication == null) return ApiResponse.onFailure("409", "이름이 중복됩니다.", "");
        return ApiResponse.onSuccess();
    }

    // 로그인
    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ApiResponse<?> login(@RequestParam String id, @RequestParam String password) {
        return ApiResponse.onSuccess();
    }

}
