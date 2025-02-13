package young.blaybus.domain.member.request;

/**
 * 회원가입 두번째 화면에서 보낼 데이터
 */

public record MemberSecondStepRequest(
    String id,
    String password
) { }
