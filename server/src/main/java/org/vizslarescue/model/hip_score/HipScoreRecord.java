package org.vizslarescue.model.hip_score;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import org.vizslarescue.utils.GenericEntity;
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
public class HipScoreRecord extends GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Pattern(regexp = "[A-Z][A-Z]d")
    private String brs;

    private Date date;

    @PositiveOrZero
    @Column(name = "left_score")
    private int left;

    @PositiveOrZero
    @Column(name = "right_score")
    private int right;

    private String additionalDetails;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Dog dog;

    public static EntityDescription getDescription() {
        EntityDescription description = new EntityDescription();

        description.setBusinessName("Hip Scores");
        description.setTechnicalName("hip_scores");
        description.setIcon("book-medical");
        description.setFields(Arrays.asList(
            new FieldDescription("ID", "id", FieldType.ID, 100),
            new TextFieldDescription("BRS", "brs", false, 100).regex("[A-Z][A-Z]d"),
            new FieldDescription("Date", "date", FieldType.DATE, 150),
            new FieldDescription("Left Score", "left", FieldType.NUMBER, 100),   
            new FieldDescription("Right Score", "right", FieldType.NUMBER, 100),            
            new TextFieldDescription("Additional Details", "additionalDetails", true, 300),
            new EntityFieldDescription("Dog", "dog", 100, "dogs")
        ));

        return description;
    }
}
