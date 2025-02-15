package young.blaybus.domain.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import young.blaybus.domain.certificate.request.CreateCertificateRequest;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@Builder
@Schema(description = "요양보호사 조회 응답 객체")
public class GetMember {

    @Schema(description = "이름")
    private String name;

    @Schema(description = "전화번호")
    private String phoneNumber;

    @Schema(description = "시/도")
    private String city;

    @Schema(description = "구/군")
    private String gu;

    @Schema(description = "동")
    private String dong;

    @Schema(description = "자격증")
    private List<CreateCertificateRequest> certificate;

    @Schema(description = "아이디")
    private String id;

    @Schema(description = "차량 소유 여부")
    private Boolean carYn;

    @Schema(description = "치매 교육 여부")
    private Boolean dementiaEducationYn;

    @Schema(description = "주요 경력")
    private String career;

    @Schema(description = "경력 기간")
    private String careerPeriod;

    @Schema(description = "한줄 소개")
    private String introduction;

}
