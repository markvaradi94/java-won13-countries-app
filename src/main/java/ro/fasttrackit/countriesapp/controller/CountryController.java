package ro.fasttrackit.countriesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.countriesapp.exception.ResourceNotFoundException;
import ro.fasttrackit.countriesapp.model.City;
import ro.fasttrackit.countriesapp.model.Country;
import ro.fasttrackit.countriesapp.model.CountryFilters;
import ro.fasttrackit.countriesapp.model.NeighbourRequest;
import ro.fasttrackit.countriesapp.service.CountryService;

import java.util.List;


// could add endpoints to add cities and neighbours to a specific country
@RestController
@RequestMapping("countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService service;

//    @GetMapping
//    List<Country> getAll(@RequestParam(required = false) String name,
//                         @RequestParam(required = false) String capital,
//                         @RequestParam(required = false) Long minPopulation,
//                         @RequestParam(required = false) Long maxPopulation,
//                         @RequestParam(required = false) String continent) {
//        return service.filterCountries(name, capital, maxPopulation, minPopulation, continent);
//    }

    @GetMapping
    List<Country> getAll(CountryFilters filters) {
        return service.filterCountries(filters);
    }

    @GetMapping("{id}")
    Country getById(@PathVariable long id) {
        return service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find country with id %s".formatted(id), id));
    }

    @PostMapping
    Country addCountry(@RequestBody Country newCountry) {
        return service.add(newCountry);
    }

    @DeleteMapping("{id}")
    Country deleteById(@PathVariable long id) {
        return service.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find country with id %s".formatted(id), id));
    }

    @PostMapping("{countryId}/cities")
    Country addCity(@PathVariable long countryId, @RequestBody City city) {
        return service.addCityToCountry(countryId, city);
    }

    @PostMapping("{countryId}/neighbours")
    Country addNeighbour(@PathVariable long countryId, @RequestBody NeighbourRequest neighbour) {
        return service.addNeighbourToCountry(countryId, neighbour);
    }
}
