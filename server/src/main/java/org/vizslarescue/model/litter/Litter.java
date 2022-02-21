package org.vizslarescue.model.litter;

import java.util.Arrays;
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
import org.vizslarescue.model.metadata.EntityDescription;
import org.vizslarescue.model.metadata.FieldDescription;
import org.vizslarescue.model.metadata.FieldType;
import org.vizslarescue.model.metadata.TextFieldDescription;

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

    public static EntityDescription getDescription() {
        EntityDescription description = new EntityDescription();

        description.setBusinessName("Litters");
        description.setTechnicalName("litters");
        description.setIcon("paw");
        description.setFields(Arrays.asList(
            new FieldDescription("ID", "id", FieldType.ID, 100),
            new TextFieldDescription("BRS", "brs", false, 100).regex("[A-Z][A-Z][0-9]"),
            new FieldDescription("Date", "date", FieldType.DATE, 150),
            new FieldDescription("Was Cesarean", "wasCesarean", FieldType.BOOLEAN, 100),
            new TextFieldDescription("Additional Details", "additionalDetails", true, 300),
            new FieldDescription("Breeder", "breeder", FieldType.ENTITY, 100),
            new FieldDescription("Sire", "sire", FieldType.ENTITY, 100),
            new FieldDescription("Dam", "dam", FieldType.ENTITY, 100)
        ));

        return description;
    }
}