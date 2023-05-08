package org.vizslarescue.model.dog;

import java.util.Arrays;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import org.vizslarescue.model.elbow_score.ElbowScoreRecord;
import org.vizslarescue.model.hip_score.HipScoreRecord;
import org.vizslarescue.utils.GenericEntity;
import org.vizslarescue.model.litter.Litter;
import org.vizslarescue.model.metadata.EntityDescription;
import org.vizslarescue.model.metadata.EntityFieldDescription;
import org.vizslarescue.model.metadata.FieldDescription;
import org.vizslarescue.model.metadata.FieldType;
import org.vizslarescue.model.metadata.TextFieldDescription;

import lombok.Data;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class Dog extends GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private Gender gender;

    private String additionalDetails;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("puppies")
    private Litter litter;

    @OneToMany(mappedBy="dog", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("dog")
    private List<ElbowScoreRecord> elbowScores;

    @OneToMany(mappedBy="dog", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("dog")
    private List<HipScoreRecord> hipScores;

    public static EntityDescription getDescription() {
        EntityDescription description = new EntityDescription();

        description.setBusinessName("Dogs");
        description.setTechnicalName("dogs");
        description.setIcon("dog");
        description.setAlternativeKey("name");
        description.setFields(Arrays.asList(
            new FieldDescription("ID", "id", FieldType.ID, 100),
            new TextFieldDescription("Name", "name", false, 200),
            new TextFieldDescription("Gender", "gender", false, 100).acceptedValues(Gender.values()),
            new TextFieldDescription("Additional Details", "additionalDetails", true, 300),
            new EntityFieldDescription("Litter", "litter", 100, "litters")
        ));

        return description;
    }
}
