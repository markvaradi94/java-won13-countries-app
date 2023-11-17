package ro.fasttrackit.countriesapp.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "countries")
public class CountryConfig {
    private String filePath;

    @PostConstruct
    void printConfig() {
        System.out.println("File path: " + filePath);
    }

    public String filePath() {
        return filePath;
    }
}
