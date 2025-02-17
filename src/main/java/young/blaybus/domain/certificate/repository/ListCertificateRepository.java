package young.blaybus.domain.certificate.repository;

import static young.blaybus.domain.certificate.QCertificate.certificate;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import young.blaybus.domain.certificate.Certificate;
import young.blaybus.domain.certificate.enums.CertificateType;
import young.blaybus.domain.member.Member;

@Repository
@RequiredArgsConstructor
public class ListCertificateRepository {

  private final JPAQueryFactory queryFactory;


  public List<Certificate> getCertificateList(Member member) {
    return queryFactory
      .selectFrom(certificate)
      .where(
        certificate.member.eq(member),
        certificate.type.ne(CertificateType.CARE)
      )
      .fetch();

  }
}
