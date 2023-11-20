package ro.fasttrackit.countriesapp.reader;

import org.springframework.stereotype.Component;
import ro.fasttrackit.countriesapp.config.CountryConfig;
import ro.fasttrackit.countriesapp.exception.ResourceReadingException;
import ro.fasttrackit.countriesapp.model.City;
import ro.fasttrackit.countriesapp.model.Continent;
import ro.fasttrackit.countriesapp.model.Country;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class CountryReader {
    private final CountryConfig config;

    public CountryReader(CountryConfig config) {
        this.config = config;
    }

    private static long ID_COUNTER = 1;

    public List<Country> readCountries() {
        try (BufferedReader reader = new BufferedReader(new FileReader(config.filePath()))) {
            return reader.lines()
                    .map(this::mapToCountry)
                    .toList();
        } catch (IOException e) {
            throw new ResourceReadingException(e.getMessage());
        }
    }

    private Country mapToCountry(String line) {
        String[] tokens = line.split("[|]");
        long id = ID_COUNTER++;
        return Country.builder()
                .id(id)
                .name(tokens[0])
                .capital(City.builder()
                        .name(tokens[1])
                        .build())
                .population(Long.parseLong(tokens[2]))
                .area(Long.parseLong(tokens[3]))
                .continent(Continent.of(tokens[4]))
//                .neighbours(tokens.length > 5 ? extractNeighbours(tokens[5]) : List.of())
                .neighbours(List.of())
                .build();
    }

    private List<String> extractNeighbours(String input) {
        return Arrays.asList(input.split("~"));
    }
}
