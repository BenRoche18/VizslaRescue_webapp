package org.vizslarescue.model.dog;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ElbowScore {
    private Date date_of_test;
    private int score;
}
