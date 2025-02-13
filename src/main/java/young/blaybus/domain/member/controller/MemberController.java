package young.blaybus.domain.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import young.blaybus.api_response.ApiResponse;
import young.blaybus.domain.member.request.MemberRequest;
import young.blaybus.domain.member.request.MemberFirstStepRequest;
import young.blaybus.domain.member.request.MemberSecondStepRequest;
import young.blaybus.domain.member.request.MemberThirdStepRequest;
import young.blaybus.domain.member.service.MemberService;

/**
 * 회원가입을 여러 단계별로 진행한다.
 * 회원은 관리자, 요양보호사로 구성된다. 그렇기 때문에 요양보호사와 관리자 회원가입 방식은 살짝 다르게 가져간다. 그래서 먼저 요양보호사인지 관리자인지 판단하는 것이 필요하다.
 */

@Controller
@RequestMapping("/api/v3/member")
@RequiredArgsConstructor
@Tag(name = "회원 관련 API")
public class MemberController {

    private final MemberService memberService;

    // 요양보호사인지 관리자인지 체크
    @PostMapping("/{type}")
    @ResponseBody
    public ResponseEntity<?> memberTypeCheck(@PathVariable("type") String type, HttpServletResponse response) {
        memberService.memberTypeCheck(type, response);
        return ResponseEntity.ok().build();
    }

    // 스탭별로 회원가입 진행 (제일 끝 단계는 회원가입을 진행 -> DB에 저장)
    @PostMapping("/join/step/1")
    @ResponseBody
    public ResponseEntity<?> workerJoinFirstStep(HttpServletRequest request, @RequestBody MemberFirstStepRequest memberRequest) {
        memberService.workerJoinFirstStep(request, memberRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/join/step/2")
    @ResponseBody
    public ResponseEntity<?> workerJoinSecondStep(HttpServletRequest request, @RequestBody MemberSecondStepRequest memberRequest) {
        memberService.workerJoinSecondStep(request, memberRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/join/step/3")
    @ResponseBody
    public ResponseEntity<?> workerJoinThirdStep(
         HttpServletRequest request,
         @RequestBody MemberThirdStepRequest memberRequest,
         HttpServletResponse response
    ) {
        MemberRequest memberDto = memberService.workerJoinThirdStep(request, memberRequest);
        memberService.registerMember(memberDto, request, response);
        return ResponseEntity.ok().build();
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
