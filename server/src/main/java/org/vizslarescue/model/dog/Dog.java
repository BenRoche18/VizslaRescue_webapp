package org.vizslarescue.model.dog;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Dogs")
public class Dog {
    @Id
    private String id;

    private String name;
    private String gender;
    private String litter_id;
    private String additionalDetails;

    private HipScore hipScore;
}