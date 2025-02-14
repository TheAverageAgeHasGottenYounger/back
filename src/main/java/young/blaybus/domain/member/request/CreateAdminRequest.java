package young.blaybus.domain.member.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import young.blaybus.domain.center.request.CreateCenterRequest;

@Getter
@Setter
@ToString
@Schema(description = "관리자 회원가입 요청 객체")
public class CreateAdminRequest {

    @Schema(description = "전화번호")
    private String phoneNumber;

    @Schema(description = "주소 - 시")
    private String city;

    @Schema(description = "주소 - 구")
    private String gu;

    @Schema(description = "주소 - 동")
    private String dong;

    @Schema(description = "차량 소유 여부")
    private Boolean carYn;

    @Schema(description = "아이디")
    private String id;

    @Schema(description = "비밀번호")
    private String password;

    @Schema(description = "센터")
    private CreateCenterRequest center;
}
