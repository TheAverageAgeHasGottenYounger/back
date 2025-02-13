package young.blaybus.domain.member.request;

import io.swagger.v3.oas.annotations.media.Schema;
import young.blaybus.domain.center.request.CreateCenterRequest;

@Schema(description = "관리자 회원가입 요청 객체")
public record CreateAdminRequest(
    String profileImage,

    @Schema(description = "이름")
    String name,

    @Schema(description = "전화번호")
    String phoneNumber,

    @Schema(description = "주소")
    String address,

    @Schema(description = "아이디")
    String id,

    @Schema(description = "비밀번호")
    String password,

    @Schema(description = "권한")
    String role,

    @Schema(description = "센터")
    CreateCenterRequest center
) { }
