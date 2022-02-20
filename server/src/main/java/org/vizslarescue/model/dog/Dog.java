package org.vizslarescue.model.dog;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.vizslarescue.Utils.GenericEntity;
import org.vizslarescue.model.litter.Litter;
import org.vizslarescue.model.metadata.EntityDescription;
import org.vizslarescue.model.metadata.FieldDescription;
import org.vizslarescue.model.metadata.FieldType;
import org.vizslarescue.model.metadata.TextFieldDescription;

import lombok.Data;

@Data
@Entity
public class Dog extends GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank
    private String name;
    @NotNull
    private Gender gender;
    private String additionalDetails;

    @OneToOne
    private Litter litter;

    public static EntityDescription getDescription() {
        EntityDescription description = new EntityDescription();

        description.setBusinessName("Dogs");
        description.setTechnicalName("dogs");
        description.setIcon("dog");
        description.setFields(Arrays.asList(
            new FieldDescription("ID", "id", FieldType.ID),
            new TextFieldDescription("Name", "name", false),
            new TextFieldDescription("Gender", "gender", false).acceptedValues(Gender.values()),
            new TextFieldDescription("Additional Details", "additionalDetails", true),
            new FieldDescription("Litter", "litter", FieldType.ENTITY)
        ));

        return description;
    }
}