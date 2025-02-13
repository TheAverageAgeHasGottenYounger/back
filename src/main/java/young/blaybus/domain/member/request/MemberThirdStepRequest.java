package young.blaybus.domain.member.request;

/**
 * 회원가입 세번째 화면에서 보낼 데이터
 */

public record MemberThirdStepRequest(
    Boolean carYn,
    Boolean dementiaEducationYn,
    String career,
    String careerPeriod,
    String introduction
) { }
