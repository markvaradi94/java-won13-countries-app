package ro.fasttrackit.countriesapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.countriesapp.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
}
