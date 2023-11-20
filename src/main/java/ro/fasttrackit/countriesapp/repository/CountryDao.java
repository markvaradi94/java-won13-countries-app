package ro.fasttrackit.countriesapp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.countriesapp.model.Continent;
import ro.fasttrackit.countriesapp.model.Country;
import ro.fasttrackit.countriesapp.model.CountryFilters;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static ro.fasttrackit.countriesapp.model.Country.Fields.*;

@Repository
public class CountryDao {
    private final EntityManager entityManager;

    public CountryDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Country> filterCountries(CountryFilters filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);

        Root<Country> country = criteriaQuery.from(Country.class);
        List<Predicate> predicates = new ArrayList<>();

        ofNullable(filters.name())
                .ifPresent(countryName -> predicates.add(criteriaBuilder.like(criteriaBuilder.lower(country.get(name)), "%" + countryName.toLowerCase() + "%")));

        ofNullable(filters.capital())
                .ifPresent(countryCapital -> predicates.add(criteriaBuilder.equal(country.get(capital), countryCapital)));

        ofNullable(filters.minPopulation())
                .ifPresent(minPopulation -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(country.get(population), minPopulation)));

        ofNullable(filters.maxPopulation())
                .ifPresent(maxPopulation -> predicates.add(criteriaBuilder.lessThanOrEqualTo(country.get(population), maxPopulation)));

        ofNullable(filters.continent())
                .ifPresent(countryContinent -> predicates.add(criteriaBuilder.equal(country.get(continent), Continent.of(countryContinent))));

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Country> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Country> filterCountries(String countryName, String countryCapital, Long minPopulation, Long maxPopulation, String countryContinent) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);

        Root<Country> country = criteriaQuery.from(Country.class);
        List<Predicate> predicates = new ArrayList<>();

        if (countryName != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(country.get(name)), "%" + countryName.toLowerCase() + "%"));
        }
        if (countryCapital != null) {
            predicates.add(criteriaBuilder.equal(country.get(capital), countryCapital));
        }
        if (minPopulation != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(country.get(population), minPopulation));
        }
        if (maxPopulation != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(country.get(population), maxPopulation));
        }
        if (countryContinent != null) {
            predicates.add(criteriaBuilder.equal(country.get(continent), Continent.of(countryContinent)));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Country> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}

