package young.blaybus.domain.member.request;

import young.blaybus.domain.member.enums.MemberRole;

/**
 * 관리자인지 요양보호사인지 선택하는 화면에서 보낼 데이터
 */

public record RoleRequest(
    MemberRole role
) { }
