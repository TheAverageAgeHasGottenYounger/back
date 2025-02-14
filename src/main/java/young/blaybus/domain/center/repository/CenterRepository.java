package young.blaybus.domain.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.domain.center.Center;

import java.util.List;
import java.util.Optional;

public interface CenterRepository extends JpaRepository<Center, Long> {
    Optional<List<Center>> findByNameContaining(String name);
}
