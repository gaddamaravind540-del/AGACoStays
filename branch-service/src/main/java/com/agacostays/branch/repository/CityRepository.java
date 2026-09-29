package com.agacostays.branch.repository;
import com.agacostays.branch.entity.City;
import com.agacostays.branch.enums.CityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface CityRepository extends JpaRepository<City,Long>{
 Optional<City> findByCityNameIgnoreCase(String cityName);
 List<City> findByStatusOrderByCityNameAsc(CityStatus status);
}
