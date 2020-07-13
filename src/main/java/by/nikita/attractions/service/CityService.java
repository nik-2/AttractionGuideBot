package by.nikita.attractions.service;

import by.nikita.attractions.entity.City;
import java.util.List;

/**
 * The interface City service.
 */
public interface CityService {

    /**
     * Gets cities.
     *
     * @return the cities
     */
    List<City> getCities();

    /**
     * Gets city.
     *
     * @param cityId the city id
     * @return the city
     */
    City getCity(int cityId);

    /**
     * Save city.
     *
     * @param city the city
     */
    void saveCity(City city);

    /**
     * Delete city.
     *
     * @param city the city
     */
    void deleteCity(City city);

    /**
     * Gets info.
     *
     * @param cityName the city name
     * @return the info
     */
    String getInfo(String cityName);

}
