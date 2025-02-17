package young.blaybus.map.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.map.City;
import young.blaybus.map.GuGun;

public interface GuGunRepository extends JpaRepository<GuGun, String> {

  List<GuGun> findAllByCity(City city);
}
