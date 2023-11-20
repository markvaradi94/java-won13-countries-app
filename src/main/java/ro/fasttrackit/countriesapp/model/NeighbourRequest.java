package ro.fasttrackit.countriesapp.model;

import lombok.Builder;

@Builder
public record NeighbourRequest(
        String name,
        long population,
        long area
) {
}
