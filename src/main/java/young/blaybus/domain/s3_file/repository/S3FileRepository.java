package young.blaybus.domain.s3_file.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.domain.s3_file.S3File;

public interface S3FileRepository extends JpaRepository<S3File, Long> {

  void deleteByFileName(String fileName);

  boolean existsByFileName(String fileName);

  Optional<S3File> findByFileUrl(String fileUrl);
}
