package young.blaybus.domain.matching.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.domain.matching.Matching;

import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching, Long> {
    Matching findBySenior_IdAndMember_Id(Long senior_id, String member_id);
    List<Matching> findByMember_Id(String memberId);
    List<Matching> findBySenior_Id(Long seniorId);
}
