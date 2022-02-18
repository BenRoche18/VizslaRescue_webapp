package org.vizslarescue.model.dog;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.vizslarescue.model.hip_score.HipScoreRecord;
import org.vizslarescue.model.litter.Litter;

import lombok.Data;

@Data
@Entity
public class Dog {
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

    @OneToOne
    private HipScoreRecord hipScoreRecord;

    @OneToOne
    private HipScoreRecord elbowScoreRecord;
}