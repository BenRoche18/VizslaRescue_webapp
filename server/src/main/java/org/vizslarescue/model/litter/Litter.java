package org.vizslarescue.model.litter;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Litters")
public class Litter {
    @Id
    private String id;

    private String brs;
    private String breeders;
    private Date date_of_litter;
    private boolean was_cesarean;

    private Dam dam;
    private Sire sire;
}