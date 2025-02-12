package young.blaybus.domain.job_search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.domain.job_search.JobSearch;

public interface JobSearchRepository extends JpaRepository<JobSearch, Long> {

}
