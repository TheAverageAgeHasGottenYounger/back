package young.blaybus.domain.certificate.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CertificateType {

  CARE("요양보호사"),
  SOCIAL("사회복지사"),
  NURSE("간호조무사");

  private final String value;
}
