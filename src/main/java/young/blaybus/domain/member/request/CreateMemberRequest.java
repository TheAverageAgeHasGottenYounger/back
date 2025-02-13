package young.blaybus.domain.member.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;
import young.blaybus.domain.certificate.request.CreateCertificateRequest;

import java.util.List;

@Schema(description = "요양보호사 회원가입 요청 객체")
public record CreateMemberRequest(
    @Schema(description = "프로필 사진")
    MultipartFile profileImage,

    @Schema(description = "이름")
    String name,

    @Schema(description = "전화번호")
    String phoneNumber,

    @Schema(description = "주소")
    String address,

    @Schema(description = "자격증")
    List<CreateCertificateRequest> certificate,

    @Schema(description = "아이디")
    String id,

    @Schema(description = "비밀번호")
    String password,

    @Schema(description = "차량 소유 여부")
    Boolean carYn,

    @Schema(description = "치매 교육 여부")
    Boolean dementiaEducationYn,

    @Schema(description = "주요 경력")
    String career,

    @Schema(description = "경력 기간")
    String careerPeriod,

    @Schema(description = "한줄 소개")
    String introduction
) { }
