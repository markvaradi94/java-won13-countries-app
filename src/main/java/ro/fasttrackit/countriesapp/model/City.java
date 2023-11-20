package ro.fasttrackit.countriesapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import static jakarta.persistence.GenerationType.IDENTITY;
import static ro.fasttrackit.countriesapp.model.Country.Fields.capital;

@Data
@Entity
@Builder
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore // doesn't add into the json string the field marked to be ignored
    @OneToOne(mappedBy = capital)
    private Country capitalOf;

    @JsonIgnore
    @ManyToOne  // COUNTRY_ID --> foreign key to COUNTRY(ID)
    private Country country;
}
