package young.blaybus.domain.member.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;
import young.blaybus.domain.center.request.CreateCenterRequest;

@Schema(description = "관리자 회원가입 요청 객체")
public record CreateAdminRequest(
    @Schema(description = "프로필 사진")
    MultipartFile profileImage,

    @Schema(description = "이름")
    String name,

    @Schema(description = "전화번호")
    String phoneNumber,

    @Schema(description = "주소")
    String address,

    @Schema(description = "차량 소유 여부")
    Boolean carYn,

    @Schema(description = "아이디")
    String id,

    @Schema(description = "비밀번호")
    String password,

    @Schema(description = "센터")
    CreateCenterRequest center
) { }
