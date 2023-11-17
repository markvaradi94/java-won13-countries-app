package ro.fasttrackit.countriesapp.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.countriesapp.exception.ResourceNotFoundException;
import ro.fasttrackit.countriesapp.model.Continent;
import ro.fasttrackit.countriesapp.model.Country;
import ro.fasttrackit.countriesapp.reader.CountryReader;
import ro.fasttrackit.countriesapp.repository.CountryRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryReader reader;
    private final CountryRepository repository;

    @PostConstruct
    void init() {
        repository.saveAll(reader.readCountries());
    }

    public List<Country> allCountries() {
        return repository.findAll();
    }

    public Country add(Country newCountry) {
        return repository.save(newCountry);
    }

    public Optional<Country> delete(int id) {
        Optional<Country> countryOptional = findById(id);
        countryOptional.ifPresent(repository::delete);
        return countryOptional;
    }

    public List<String> allCountryNames() {
        return repository.findAll().stream()
                .map(Country::getName)
                .toList();
    }

    public List<Country> countriesInContinent(String continentName) {
        return repository.findAll().stream()
                .filter(country -> country.getContinent() == Continent.of(continentName))
                .toList();
    }

    public List<Country> countriesInContinentWithMinPopulation(String continentName, long minPopulation) {
        return repository.findAll().stream()
                .filter(country -> country.getContinent() == Continent.of(continentName))
                .filter(country -> country.getPopulation() >= minPopulation)
                .toList();
    }

    public List<Country> countriesByNeighbours(String include, String exclude) {
        return repository.findAll().stream()
                .filter(country -> checkNeighbours(include, exclude, country))
                .toList();
    }

    public List<Country> islandCountries() {
        return repository.findAll().stream()
                .filter(country -> country.getNeighbours().isEmpty())
                .toList();
    }

    public Country lowestPopulationCountry() {
        return repository.findAll().stream()
                .min(Comparator.comparing(Country::getPopulation))
                .orElseThrow(() -> new RuntimeException("Error encountered while trying to find the country with the lowest population"));
    }

    public Country highestPopulationCountry() {
        return repository.findAll().stream()
                .max(Comparator.comparing(Country::getPopulation))
                .orElseThrow(() -> new RuntimeException("Error encountered while trying to find the country with the highest population"));
    }

    public Country lowestAreaCountry() {
        return repository.findAll().stream()
                .min(Comparator.comparing(Country::getArea))
                .orElseThrow(() -> new RuntimeException("Error encountered while trying to find the country with the lowest are"));
    }

    public Country highestAreaCountry() {
        return repository.findAll().stream()
                .max(Comparator.comparing(Country::getArea))
                .orElseThrow(() -> new RuntimeException("Error encountered while trying to find the country with the highest population"));
    }

    public List<String> countryNeighbours(int countryId) {
        return repository.findById(countryId)
                .map(Country::getNeighbours)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find country with id %s".formatted(countryId), countryId));
    }

    public Long countryPopulation(int countryId) {
        return repository.findById(countryId)
                .map(Country::getPopulation)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find country with id %s".formatted(countryId), countryId));
    }

    public String countryCapital(int countryId) {
        return repository.findById(countryId)
                .map(Country::getCapital)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find country with id %s".formatted(countryId), countryId));
    }

    private boolean checkNeighbours(String include, String exclude, Country country) {
        return country.getNeighbours().contains(include.toUpperCase()) && !country.getNeighbours().contains(exclude.toUpperCase());
    }

    public Optional<Country> findById(int id) {
        return repository.findById(id);
    }
}
