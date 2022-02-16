package org.vizslarescue.model.dog;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;
    // private String gender;
    // private String litter_id;
    // private String additional_details;

    // private HipScore hip_score;
    // private ElbowScore elbow_score;
}