package by.nikita.attractions.service;

import by.nikita.attractions.dao.CityRepository;
import by.nikita.attractions.entity.City;
import by.nikita.attractions.util.WikiWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type City service.
 */
@Service
public class CityServiceImpl implements CityService {

    private CityRepository cityRepository;

    /**
     * Instantiates a new City service.
     *
     * @param cityRepository the city repository
     */
    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public City getCity(int cityId) {
        Optional<City> optionalCity = cityRepository.findById(cityId);
        City city = null;
        if (optionalCity.isPresent()) {
            city = optionalCity.get();
        }
        return city;
    }

    @Override
    public void saveCity(City city) {
        cityRepository.save(city);
    }

    @Override
    public void deleteCity(City city) {
        cityRepository.delete(city);
    }

    @Override
    public String getInfo(String cityName) {
        StringBuilder info = new StringBuilder();
        WikiWorker wikiWorker = new WikiWorker();
        List<City> cities = cityRepository.findAllByName(cityName);
        if (cities.isEmpty()) {
            info.append("Мои бот архивы не имеют информации об этом городе...");
            String infoByWki = wikiWorker.getInfoByWiki(cityName);
            if (infoByWki == null) {
                info.append("\n\nМои ботодрузья из википедии также ничего не нашли. Попробуйте снова.");
            } else {
                info.append("\n\nОбращаюсь к своим ботодрузьям из википедии...").append(infoByWki)
                        .append("...\nПолная статья доступна по ссылке: https://ru.m.wikipedia.org/wiki/").append(cityName);
            }
        } else {
            cities.forEach(city -> info.append(city.getInfo()).append("\n"));
        }
        return info.toString();
    }
}
