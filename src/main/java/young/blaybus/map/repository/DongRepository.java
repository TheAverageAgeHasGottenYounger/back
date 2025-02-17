package young.blaybus.map.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.map.Dong;
import young.blaybus.map.GuGun;

public interface DongRepository extends JpaRepository<Dong, String> {

  List<Dong> findAllByGuGun(GuGun guGun);
}
