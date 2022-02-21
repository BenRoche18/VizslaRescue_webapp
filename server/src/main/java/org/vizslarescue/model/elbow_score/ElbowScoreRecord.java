package org.vizslarescue.model.elbow_score;

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
public class ElbowScoreRecord extends GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Pattern(regexp = "[A-Z][A-Z][0-9]")
    private String brs;
    private Date date;
    @PositiveOrZero
    private int score;
    private String additionalDetails;

    @NotNull
    @ManyToOne
    private Dog dog;

    public static EntityDescription getDescription() {
        EntityDescription description = new EntityDescription();

        description.setBusinessName("Elbow Scores");
        description.setTechnicalName("elbow_scores");
        description.setIcon("book-medical");
        description.setFields(Arrays.asList(
            new FieldDescription("ID", "id", FieldType.ID, 100),
            new TextFieldDescription("BRS", "brs", false, 100).regex("[A-Z][A-Z][0-9]"),
            new FieldDescription("Date", "date", FieldType.DATE, 150),
            new FieldDescription("Score", "score", FieldType.NUMBER, 100),            
            new TextFieldDescription("Additional Details", "additionalDetails", true, 300),
            new FieldDescription("Dog", "dog", FieldType.ENTITY, 100)
        ));

        return description;
    }
}
