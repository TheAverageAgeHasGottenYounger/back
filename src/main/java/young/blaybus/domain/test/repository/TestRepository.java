package young.blaybus.domain.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.domain.test.Test;

public interface TestRepository extends JpaRepository<Test, Long> {

}
