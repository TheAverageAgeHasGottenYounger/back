package young.blaybus.domain.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import young.blaybus.domain.member.enums.MemberRole;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "현재 로그인 멤버 정보 조회 응답 객체")
public class CurrentMemberResponse {

  @Schema(description = "멤버 ID")
  private String memberId;

  @Schema(description = "역할")
  private MemberRole role;

}
