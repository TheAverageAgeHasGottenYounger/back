package young.blaybus.domain.certificate.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CertificateGrade {

  GRADE1("1급"),
  GRADE2("2급");

  private final String value;
}
