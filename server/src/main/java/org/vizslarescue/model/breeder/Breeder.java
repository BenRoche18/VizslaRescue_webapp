package org.vizslarescue.model.breeder;

import java.util.Arrays;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import org.vizslarescue.model.dog.Dog;
import org.vizslarescue.model.litter.Litter;
import org.vizslarescue.utils.GenericEntity;
import org.vizslarescue.model.metadata.EntityDescription;
import org.vizslarescue.model.metadata.FieldDescription;
import org.vizslarescue.model.metadata.FieldType;
import org.vizslarescue.model.metadata.TextFieldDescription;

import lombok.Data;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class Breeder extends GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank
    private String names;

    private String additionalDetails;

    @OneToMany(mappedBy="breeder", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("breeder")
    private List<Litter> litters;

    public static EntityDescription getDescription() {
        EntityDescription description = new EntityDescription();

        description.setBusinessName("Breeders");
        description.setTechnicalName("breeders");
        description.setIcon("user");
        description.setAlternativeKey("names");
        description.setFields(Arrays.asList(
            new FieldDescription("ID", "id", FieldType.ID, 100),
            new TextFieldDescription("Names", "names", false, 200),
            new TextFieldDescription("Additional Details", "additionalDetails", true, 300)
        ));

        return description;
    }
}
