package young.blaybus.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import young.blaybus.domain.center.controller.request.CreateCenterRequest;

@Schema(description = "관리자 회원가입 요청 객체")
public record CreateAdminRequest(
    @Schema(description = "전화번호")
    String phoneNumber,

    @Schema(description = "시/도")
    String city,

    @Schema(description = "구/군")
    String gu,

    @Schema(description = "동")
    String dong,

    @Schema(description = "차량 소유 여부")
    Boolean carYn,

    @Schema(description = "아이디")
    String id,

    @Schema(description = "비밀번호")
    String password,

    @Schema(description = "센터")
    CreateCenterRequest center
) { }
