package young.blaybus.domain.job_search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.domain.job_search.JobSearch;

import java.util.Optional;

public interface JobSearchRepository extends JpaRepository<JobSearch, Long> {
  Optional<JobSearch> findByMemberId(String member_id);
}
