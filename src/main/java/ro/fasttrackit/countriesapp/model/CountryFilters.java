package ro.fasttrackit.countriesapp.model;

import lombok.Builder;

@Builder
public record CountryFilters(
        String name,
        String capital,
        Long maxPopulation,
        Long minPopulation,
        String continent
) {
}
