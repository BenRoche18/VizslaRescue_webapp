package org.vizslarescue.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "BreedRecordSupplement")
public class Dog {
    @Id
    private String id;

    private String name;
    private String breeders;
    private String gender;
    private String brs;
    private Date dob;
    private Boolean wasCesarean;
    private Date lastLitter;
    private String additionalDetails;

    private HipScore hipScore;

    private String damId;
    private String sireId;
}