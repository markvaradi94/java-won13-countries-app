package ro.fasttrackit.countriesapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static ro.fasttrackit.countriesapp.model.City.Fields.country;

@Data
@Entity
@Builder
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    //    @Column(name = "country_name")
    private String name;

    @OneToOne(cascade = ALL) // when a persist (save) inside the db happens, cascade it to the city entity too
    private City capital;

    private long population;

    private long area;

    @Enumerated(STRING)
    private Continent continent;

    @OneToMany(mappedBy = country)
    private List<City> cities;

    //@Transient // not persisted to the db --> something that is just intermediary
    @JsonIgnore
    @ManyToMany
    private List<Country> neighbours;
}
