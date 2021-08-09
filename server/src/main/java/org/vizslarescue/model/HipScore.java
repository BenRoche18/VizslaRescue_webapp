package org.vizslarescue.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class HipScore {
    private Date date;
    private int left;
    private int right;
}
