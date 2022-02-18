package org.vizslarescue.model.elbow_score;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
@Entity
public class ElbowScoreRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Pattern(regexp = "[A-Z][A-Z][0-9]")
    private String brs;
    private Date date;
    @PositiveOrZero
    private int score;
}