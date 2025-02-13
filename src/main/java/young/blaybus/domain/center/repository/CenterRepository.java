package young.blaybus.domain.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.domain.center.Center;

import java.util.Optional;

public interface CenterRepository extends JpaRepository<Center, Long> {
    Optional<Center> findByNameContaining(String name);
}
