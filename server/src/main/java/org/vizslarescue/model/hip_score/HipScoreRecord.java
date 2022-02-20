package org.vizslarescue.model.hip_score;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.vizslarescue.Utils.GenericEntity;
import org.vizslarescue.model.dog.Dog;
import org.vizslarescue.model.metadata.EntityDescription;
import org.vizslarescue.model.metadata.FieldDescription;
import org.vizslarescue.model.metadata.FieldType;
import org.vizslarescue.model.metadata.TextFieldDescription;

import lombok.Data;

@Data
@Entity
public class HipScoreRecord extends GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Pattern(regexp = "[A-Z][A-Z][0-9]")
    private String brs;
    private Date date;
    @PositiveOrZero
    private int left;
    @PositiveOrZero
    private int right;
    private String additionalDetails;

    @NotNull
    @ManyToOne
    private Dog dog;

    public static EntityDescription getDescription() {
        EntityDescription description = new EntityDescription();

        description.setBusinessName("Hip Scores");
        description.setTechnicalName("hip_scores");
        description.setIcon("book-medical");
        description.setFields(Arrays.asList(
            new FieldDescription("ID", "id", FieldType.ID),
            new TextFieldDescription("BRS", "brs", false).regex("[A-Z][A-Z][0-9]"),
            new FieldDescription("Date", "date", FieldType.DATE),
            new FieldDescription("Left Score", "left", FieldType.NUMBER),   
            new FieldDescription("Right Score", "right", FieldType.NUMBER),            
            new TextFieldDescription("Additional Details", "additionalDetails", true),
            new FieldDescription("Dog", "dog", FieldType.ENTITY)
        ));

        return description;
    }
}
