package ro.fasttrackit.countriesapp.model;

import lombok.Builder;

import java.util.List;

@Builder
public record CountryRecord(
        Integer id,

        String name,
        String capital,
        Long population,
        Long area,
        Continent continent,
        List<String> neighbours
) {
}
