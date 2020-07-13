package by.nikita.attractions.dao;

import by.nikita.attractions.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface City repository.
 */
public interface CityRepository extends JpaRepository<City, Integer> {
    /**
     * Find all by name list.
     *
     * @param cityName the city name
     * @return the list
     */
    List<City> findAllByName(String cityName);
}
