package by.nikita.attractions.controller;

import by.nikita.attractions.customexception.CityNotFoundException;
import by.nikita.attractions.entity.City;
import by.nikita.attractions.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Rest controller.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/travel")
public class RestController {

    private CityService cityService;

    /**
     * Instantiates a new Rest controller.
     *
     * @param cityService the city service
     */
    @Autowired
    public RestController(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Gets cities.
     *
     * @return the cities
     */
    @GetMapping("/attraction")
    public List<City> getCities() {
        return cityService.getCities();
    }

    /**
     * Gets city.
     *
     * @param cityId the city id
     * @return the city
     * @throws CityNotFoundException the city not found exception
     */
    @GetMapping("/attraction/{cityId}")
    public City getCity(@PathVariable int cityId) throws CityNotFoundException {
        City city = cityService.getCity(cityId);

        if(city == null){
            throw new CityNotFoundException("City with id : " + cityId + " - not found");
        }
        return city;
    }

    /**
     * Update city city.
     *
     * @param city the city
     * @return the city
     */
    @PutMapping("/attraction")
    public City updateCity(@RequestBody City city) {
        cityService.saveCity(city);

        return city;
    }

    /**
     * Save city city.
     *
     * @param city the city
     * @return the city
     */
    @PostMapping("/attraction")
    public City saveCity(@RequestBody City city) {
        city.setId(0);

        cityService.saveCity(city);

        return city;
    }

    /**
     * Delete city string.
     *
     * @param cityId the city id
     * @return the string
     * @throws CityNotFoundException the city not found exception
     */
    @DeleteMapping("/attraction/{cityId}")
    public String deleteCity(@PathVariable int cityId) throws CityNotFoundException {

        City city = cityService.getCity(cityId);

        if(city == null){
            throw new CityNotFoundException("City with id : " + cityId + " - not found");
        }

        cityService.deleteCity(city);

        return "City with id = " + cityId + " was successful deleted";
    }
}