package young.blaybus.domain.certificate.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Schema(description = "자격증 정보 생성 요청 객체")
public class CreateCertificateRequest {
    @Schema(description = "종류")
    private String type;

    @Schema(description = "번호")
    private String number;

    @Schema(description = "등급")
    private String grade;
}
