package young.blaybus.domain.certificate.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "자격증 목록 응답 DTO")
public class ListCertificateDto {

  private String type;

  private String number;

  private String grade;

}
