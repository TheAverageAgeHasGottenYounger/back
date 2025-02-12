package young.blaybus.domain.job_condition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.domain.job_condition.JobCondition;

public interface JobConditionRepository extends JpaRepository<JobCondition, Long> {

}
