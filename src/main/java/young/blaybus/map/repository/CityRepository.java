package young.blaybus.map.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.blaybus.map.City;

public interface CityRepository extends JpaRepository<City, String> {

}
