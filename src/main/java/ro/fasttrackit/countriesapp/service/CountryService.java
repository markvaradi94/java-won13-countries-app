package ro.fasttrackit.countriesapp.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.countriesapp.exception.ResourceNotFoundException;
import ro.fasttrackit.countriesapp.model.*;
import ro.fasttrackit.countriesapp.reader.CountryReader;
import ro.fasttrackit.countriesapp.repository.CityRepository;
import ro.fasttrackit.countriesapp.repository.CountryDao;
import ro.fasttrackit.countriesapp.repository.CountryRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryReader reader;
    private final CountryDao dao;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    @PostConstruct
    void init() {
        List<Country> countries = countryRepository.saveAll(reader.readCountries());
        countries.forEach(country -> country.getCapital().setCountry(country));

        cityRepository.saveAll(countries.stream()
                .map(Country::getCapital)
                .toList());
    }

//    public List<Country> filterCountries(String name,
//                                         String capital,
//                                         Long maxPopulation,
//                                         Long minPopulation,
//                                         String continent) {
//        return dao.filterCountries(name, capital, minPopulation, maxPopulation, continent);
//    }

    public List<Country> filterCountries(CountryFilters filters) {
        return dao.filterCountries(filters);
    }


    public List<Country> allCountries() {
        return countryRepository.findAll();
    }

    public List<Country> countriesByContinent(String continentName) {
//        return repository.findByContinentJPQL(Continent.of(continentName));
//        return repository.findByContinentNativeQuery(continentName.toUpperCase());
        return countryRepository.findByContinent(Continent.of(continentName));
    }

    public Country add(Country newCountry) {
        return countryRepository.save(newCountry);
    }

    public Country addCityToCountry(long id, City city) {
        Country country = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find country with id %s".formatted(id), id));
        city.setCountry(country);
        country.getCities().add(city);
        cityRepository.save(city);
        return countryRepository.save(country);
    }

    public Country addNeighbourToCountry(long countryId, NeighbourRequest neighbour) {
        Country country = findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find country with id %s".formatted(countryId), countryId));
        Country newNeighbour = Country.builder()
                .name(neighbour.name())
                .area(neighbour.area())
                .population(neighbour.population())
                .continent(country.getContinent())
                .build();
        countryRepository.save(newNeighbour);
        country.getNeighbours().add(newNeighbour);
        return countryRepository.save(country);
    }

    public Optional<Country> delete(long id) {
        Optional<Country> countryOptional = findById(id);
        countryOptional.ifPresent(countryRepository::delete);
        return countryOptional;
    }

    public List<String> allCountryNames() {
        return countryRepository.findAll().stream()
                .map(Country::getName)
                .toList();
    }

    public List<Country> countriesInContinent(String continentName) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getContinent() == Continent.of(continentName))
                .toList();
    }

    public List<Country> countriesInContinentWithMinPopulation(String continentName, long minPopulation) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getContinent() == Continent.of(continentName))
                .filter(country -> country.getPopulation() >= minPopulation)
                .toList();
    }

    public List<Country> countriesByNeighbours(String include, String exclude) {
        return countryRepository.findAll().stream()
                .filter(country -> checkNeighbours(include, exclude, country))
                .toList();
    }

    public List<Country> islandCountries() {
        return countryRepository.findAll().stream()
                .filter(country -> country.getNeighbours().isEmpty())
                .toList();
    }

    public Country lowestPopulationCountry() {
        return countryRepository.findAll().stream()
                .min(Comparator.comparing(Country::getPopulation))
                .orElseThrow(() -> new RuntimeException("Error encountered while trying to find the country with the lowest population"));
    }

    public Country highestPopulationCountry() {
        return countryRepository.findAll().stream()
                .max(Comparator.comparing(Country::getPopulation))
                .orElseThrow(() -> new RuntimeException("Error encountered while trying to find the country with the highest population"));
    }

    public Country lowestAreaCountry() {
        return countryRepository.findAll().stream()
                .min(Comparator.comparing(Country::getArea))
                .orElseThrow(() -> new RuntimeException("Error encountered while trying to find the country with the lowest are"));
    }

    public Country highestAreaCountry() {
        return countryRepository.findAll().stream()
                .max(Comparator.comparing(Country::getArea))
                .orElseThrow(() -> new RuntimeException("Error encountered while trying to find the country with the highest population"));
    }

//    public List<String> countryNeighbours(int countryId) {
//        return countryRepository.findById(countryId)
//                .map(Country::getNeighbours)
//                .orElseThrow(() -> new ResourceNotFoundException("Could not find country with id %s".formatted(countryId), countryId));
//    }

    public Long countryPopulation(long countryId) {
        return countryRepository.findById(countryId)
                .map(Country::getPopulation)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find country with id %s".formatted(countryId), countryId));
    }

    public String countryCapital(long countryId) {
        return countryRepository.findById(countryId)
                .map(Country::getCapital)
                .map(City::getName)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find country with id %s".formatted(countryId), countryId));
    }

    private boolean checkNeighbours(String include, String exclude, Country country) {
        return country.getNeighbours().contains(include.toUpperCase()) && !country.getNeighbours().contains(exclude.toUpperCase());
    }

    public Optional<Country> findById(long id) {
        return countryRepository.findById(id);
    }
}
