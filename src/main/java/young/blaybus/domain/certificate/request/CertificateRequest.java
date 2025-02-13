package young.blaybus.domain.certificate.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CertificateRequest {
    private String type;
    private String number;
    private String grade;
}
