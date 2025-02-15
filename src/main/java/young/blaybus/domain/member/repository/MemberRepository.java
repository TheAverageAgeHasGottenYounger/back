package young.blaybus.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.domain.center.Center;
import young.blaybus.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<List<Member>> findByName(String name);
}
