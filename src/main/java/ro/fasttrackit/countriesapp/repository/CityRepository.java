package ro.fasttrackit.countriesapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.countriesapp.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
