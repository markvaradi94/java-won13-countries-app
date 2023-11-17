package ro.fasttrackit.countriesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.countriesapp.exception.ResourceNotFoundException;
import ro.fasttrackit.countriesapp.model.Country;
import ro.fasttrackit.countriesapp.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService service;

    @GetMapping
    List<Country> getAll() {
        return service.allCountries();
    }

    @GetMapping("{id}")
    Country getById(@PathVariable int id) {
        return service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find country with id %s".formatted(id), id));
    }

    @PostMapping
    Country addCountry(@RequestBody Country newCountry) {
        return service.add(newCountry);
    }

    @DeleteMapping("{id}")
    Country deleteById(@PathVariable int id) {
        return service.delete(id)
                .orElseThrow(() -> new RuntimeException("Could not find country with id %s".formatted(id)));
    }
}
