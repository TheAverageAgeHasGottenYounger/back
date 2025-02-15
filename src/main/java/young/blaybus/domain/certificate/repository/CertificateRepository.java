package young.blaybus.domain.certificate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.domain.certificate.Certificate;
import young.blaybus.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Optional<List<Certificate>> findByMember(Member member);
}
