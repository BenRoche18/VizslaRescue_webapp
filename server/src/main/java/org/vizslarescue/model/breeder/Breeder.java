package org.vizslarescue.model.breeder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.vizslarescue.Utils.GenericEntity;

import lombok.Data;

@Data
@Entity
public class Breeder extends GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank
    private String names;
    private String additionalDetails;
}