package org.vizslarescue.model.dog;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class HipScore {
    private Date date_of_test;
    private int left;
    private int right;
}
