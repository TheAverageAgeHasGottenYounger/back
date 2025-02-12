package young.blaybus.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
