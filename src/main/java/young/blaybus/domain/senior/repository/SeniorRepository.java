package young.blaybus.domain.senior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.domain.senior.Senior;

import java.util.List;

public interface SeniorRepository extends JpaRepository<Senior, Long> {
    List<Senior> findByCenterId(Long centerId);
}
