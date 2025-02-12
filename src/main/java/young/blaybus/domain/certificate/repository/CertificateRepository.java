package young.blaybus.domain.certificate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.domain.certificate.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

}
