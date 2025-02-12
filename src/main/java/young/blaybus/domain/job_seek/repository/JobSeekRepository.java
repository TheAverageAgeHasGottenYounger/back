package young.blaybus.domain.job_seek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.domain.job_seek.JobSeek;

public interface JobSeekRepository extends JpaRepository<JobSeek, Long> {

}
