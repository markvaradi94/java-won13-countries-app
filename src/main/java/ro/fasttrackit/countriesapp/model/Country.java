package ro.fasttrackit.countriesapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
    private String capital;
    private long population;
    private long area;
    private Continent continent;

    @Transient // not persisted to the db --> something that is just intermediary
    private List<String> neighbours;
}
