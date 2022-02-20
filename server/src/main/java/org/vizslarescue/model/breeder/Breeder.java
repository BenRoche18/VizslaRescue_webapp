package org.vizslarescue.model.breeder;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.vizslarescue.Utils.GenericEntity;
import org.vizslarescue.model.metadata.EntityDescription;
import org.vizslarescue.model.metadata.FieldDescription;
import org.vizslarescue.model.metadata.FieldType;
import org.vizslarescue.model.metadata.TextFieldDescription;

import lombok.Data;

@Data
@Entity
public class Breeder extends GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank
    private String names;
    private String additionalDetails;

    public static EntityDescription getDescription() {
        EntityDescription description = new EntityDescription();

        description.setBusinessName("Breeders");
        description.setTechnicalName("breeders");
        description.setIcon("user");
        description.setFields(Arrays.asList(
            new FieldDescription("ID", "id", FieldType.ID),
            new TextFieldDescription("Names", "names", false),
            new TextFieldDescription("Additional Details", "additionalDetails", true)
        ));

        return description;
    }
}