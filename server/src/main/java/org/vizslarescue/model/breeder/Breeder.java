package org.vizslarescue.model.breeder;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Breeders")
public class Breeder {
    @Id
    private String id;

    private String names;
}