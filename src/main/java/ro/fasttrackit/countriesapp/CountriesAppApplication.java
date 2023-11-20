package ro.fasttrackit.countriesapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ro.fasttrackit.countriesapp.service.CountryService;

@SpringBootApplication
@EnableConfigurationProperties
public class CountriesAppApplication implements ApplicationRunner {
    @Autowired
    private CountryService service;

    public static void main(String[] args) {
        SpringApplication.run(CountriesAppApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        service.allCountries()
//                .forEach(System.out::println);

//        service.islandCountries()
//                .forEach(System.out::println);
//        System.out.println("=======================================================");
//
//        System.out.println(service.lowestPopulationCountry());
//        System.out.println("=======================================================");
//
//        service.countriesByNeighbours("rou", "hun")
//                .forEach(System.out::println);
    }
}
