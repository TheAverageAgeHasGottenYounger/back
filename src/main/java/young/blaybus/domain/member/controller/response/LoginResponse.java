package young.blaybus.domain.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import young.blaybus.domain.member.enums.MemberRole;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "로그인 성공 응답 객체")
public class LoginResponse {

  @Schema(description = "Access Token")
  private String accessToken;

  @Schema(description = "역할")
  private MemberRole role;

}
