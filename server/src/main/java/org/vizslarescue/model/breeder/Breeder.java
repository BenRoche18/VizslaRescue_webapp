package org.vizslarescue.model.breeder;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Breeders")
public class Breeder {
    @Id
    private String id;

    private String names;

    // public static AddFieldsOperation addFieldsOperation() {
    //     return Aggregation.addFields().addFieldWithValue("hip_score", MongoExpression.create("$cond:{if:\"$hip_score\",then:{$mergeObjects:[\"$hip_score\",{total:{$add:[\"$hip_score.left\",\"$hip_score.right\"]}}]},else:\"$$REMOVE\"}")).build();
    // }
}