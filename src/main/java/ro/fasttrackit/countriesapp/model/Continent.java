package ro.fasttrackit.countriesapp.model;

import java.util.Arrays;

public enum Continent {
    ASIA,
    AMERICAS,
    AFRICA,
    EUROPE,
    OCEANIA;

    public static Continent of(String name) {
        return Arrays.stream(Continent.values())
                .filter(continent -> continent.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find continent with name %s".formatted(name)));
    }
}
