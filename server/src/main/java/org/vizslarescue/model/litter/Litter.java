package org.vizslarescue.model.litter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import org.vizslarescue.utils.GenericEntity;
import org.vizslarescue.model.breeder.Breeder;
import org.vizslarescue.model.dog.Dog;
import org.vizslarescue.model.metadata.EntityDescription;
import org.vizslarescue.model.metadata.EntityFieldDescription;
import org.vizslarescue.model.metadata.FieldDescription;
import org.vizslarescue.model.metadata.FieldType;
import org.vizslarescue.model.metadata.TextFieldDescription;

import lombok.Data;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class Litter extends GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Pattern(regexp = "[A-Z][A-Z]d")
    private String brs;

    private Date date;

    private boolean wasCesarean;

    private String additionalDetails;

    @ManyToOne
    @JsonIgnoreProperties("litters")
    private Breeder breeder;

    @ManyToOne
    private Dog sire;

    @ManyToOne
    private Dog dam;

    @OneToMany(mappedBy="litter", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("litter")
    private List<Dog> puppies;

    public static EntityDescription getDescription() {
        EntityDescription description = new EntityDescription();

        description.setBusinessName("Litters");
        description.setTechnicalName("litters");
        description.setIcon("paw");
        description.setFields(Arrays.asList(
            new FieldDescription("ID", "id", FieldType.ID, 100),
            new TextFieldDescription("BRS", "brs", false, 100).regex("[A-Z][A-Z]d"),
            new FieldDescription("Date", "date", FieldType.DATE, 150),
            new FieldDescription("Was Cesarean", "wasCesarean", FieldType.BOOLEAN, 100),
            new TextFieldDescription("Additional Details", "additionalDetails", true, 300),
            new EntityFieldDescription("Breeder", "breeder", 100, "breeders"),
            new EntityFieldDescription("Sire", "sire", 100, "dogs"),
            new EntityFieldDescription("Dam", "dam", 100, "dogs")
        ));

        return description;
    }
}
