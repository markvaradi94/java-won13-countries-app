package ro.fasttrackit.countriesapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.countriesapp.model.Continent;
import ro.fasttrackit.countriesapp.model.Country;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    // #1 method naming
    // equivalent to --> select * from countries where continent=?1(firstParam)
    List<Country> findByContinent(Continent continent);


    // #2 jpql query
    // JPQL = java persistence query language
    // similar to SQL, but based on the name of the java objects and their fields
    @Query("SELECT c from Country c where c.continent=:continent")
    List<Country> findByContinentJPQL(@Param("continent") Continent continent);

    // #3 native sql query
    //
    @Query(value = "select * from country where continent=:continent", nativeQuery = true)
    List<Country> findByContinentNativeQuery(@Param("continent") String continent);
}
