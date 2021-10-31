package org.vizslarescue.model.dog;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.MongoExpression;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Dogs_Test")
public class Dog {
    @Id
    private String id;

    private String name;
    private String gender;
    private String litter_id;
    private String additional_details;

    private HipScore hip_score;
    private ElbowScore elbow_score;

    public static AddFieldsOperation addFieldsOperation() {
        return Aggregation.addFields().addFieldWithValue("hip_score", MongoExpression.create("$cond:{if:\"$hip_score\",then:{$mergeObjects:[\"$hip_score\",{total:{$add:[\"$hip_score.left\",\"$hip_score.right\"]}}]},else:\"$$REMOVE\"}")).build();
    }
}