package org.vizslarescue.model.elbow_score;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.vizslarescue.model.dog.Dog;
import org.vizslarescue.model.metadata.*;
import org.vizslarescue.utils.GenericEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.util.Arrays;
import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class ElbowScoreRecord extends GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Pattern(regexp = "[A-Z][A-Z]d")
    private String brs;

    private Date date;

    @PositiveOrZero
    private int score;

    private String additionalDetails;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("elbowScores")
    private Dog dog;

    public static EntityDescription getDescription() {
        EntityDescription description = new EntityDescription();

        description.setBusinessName("Elbow Scores");
        description.setTechnicalName("elbow_scores");
        description.setIcon("book-medical");
        description.setFields(Arrays.asList(
            new FieldDescription("ID", "id", FieldType.ID, 100),
            new TextFieldDescription("BRS", "brs", false, 100).regex("[A-Z][A-Z]d"),
            new FieldDescription("Date", "date", FieldType.DATE, 150),
            new FieldDescription("Score", "score", FieldType.NUMBER, 100),            
            new TextFieldDescription("Additional Details", "additionalDetails", true, 300),
            new EntityFieldDescription("Dog", "dog", 100, "dogs")
        ));

        return description;
    }
}
