package org.vizslarescue.model.litter;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;

import org.vizslarescue.Utils.GenericEntity;
import org.vizslarescue.model.breeder.Breeder;
import org.vizslarescue.model.dog.Dog;

import lombok.Data;

@Data
@Entity
public class Litter extends GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Pattern(regexp = "[A-Z][A-Z][0-9]")
    private String brs;
    private Date date;
    private boolean wasCesarean;
    private String additionalDetails;

    @ManyToOne
    private Breeder breeder;

    @ManyToOne
    private Dog sire;

    @ManyToOne
    private Dog dam;
}