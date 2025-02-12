package young.blaybus.domain.s3_file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.domain.s3_file.S3File;

public interface S3FileRepository extends JpaRepository<S3File, Long> {

}
