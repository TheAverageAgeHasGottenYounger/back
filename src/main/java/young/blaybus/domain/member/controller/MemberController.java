package young.blaybus.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.center.service.CenterService;
import young.blaybus.domain.member.controller.request.CreateAdminRequest;
import young.blaybus.domain.member.controller.request.CreateMemberRequest;
import young.blaybus.domain.member.controller.response.GetMember;
import young.blaybus.domain.member.service.MemberService;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "회원 관련 API")
public class MemberController {

    private final MemberService memberService;
    private final CenterService centerService;

    // 요양보호사 회원가입
    @PostMapping(value = "/worker-join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "요양보호사 회원가입")
    public ApiResponse<?> workerJoin(
        @RequestPart(value = "profileImageFile", required = false) MultipartFile profileImageFile,
        @RequestPart(value = "memberRequest") @Valid CreateMemberRequest memberRequest
    ) {
        if (profileImageFile != null) memberService.workerRegisterMember(memberRequest, profileImageFile);
        else memberService.workerRegisterMember(memberRequest, null);

        return ApiResponse.onSuccess();
    }

    // 관리자 회원가입
    @PostMapping(value = "/admin-join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "관리자 회원가입")
    public ApiResponse<?> adminJoin(
        @RequestPart(value = "profileImageFile", required = false) MultipartFile profileImageFile,
        @RequestPart(value = "adminRequest") @Valid CreateAdminRequest adminRequest
    ) {
        memberService.adminRegisterMember(adminRequest, profileImageFile);
        centerService.registerCenter(adminRequest.center(), adminRequest);

        if (profileImageFile != null){
            memberService.adminRegisterMember(adminRequest, profileImageFile);
            centerService.registerCenter(adminRequest.center(), adminRequest);
        } else {
            memberService.adminRegisterMember(adminRequest, null);
            centerService.registerCenter(adminRequest.center(), adminRequest);
        }

        return ApiResponse.onSuccess();
    }

    // 회원 아이디 중복 체크
    @GetMapping(value = "/duplication-id")
    @Operation(summary = "회원 아이디 중복 체크")
    public ApiResponse<?> duplicationIdCheck(@RequestParam String memberId) {
        String duplication = memberService.duplicationIdCheck(memberId);
        if (duplication == null) return ApiResponse.onFailure("409", "아이디가 중복됩니다.", null);
        return ApiResponse.onSuccess();
    }

    // 회원 이름 중복 체크
    @GetMapping(value = "/duplication-name")
    @Operation(summary = "회원 이름 중복 체크")
    public ApiResponse<?> duplicationNameCheck(@RequestParam String memberName) {
        String duplication = memberService.duplicationNameCheck(memberName);
        if (duplication == null) return ApiResponse.onFailure("409", "이름이 중복됩니다.", null);
        return ApiResponse.onSuccess();
    }

    // 회원 조회
    @GetMapping(value = "/{member-id}")
    @Operation(summary = "회원 조회")
    public ApiResponse<?> getMember(@PathVariable("member-id") String memberId) {
        return ApiResponse.onSuccess(memberService.getMember(memberId));
    }

    // 로그인
    @PostMapping(value = "/login")
    @Operation(summary = "로그인")
    public ApiResponse<?> login(@RequestParam String id, @RequestParam String password) {
        return ApiResponse.onSuccess();
    }

}
