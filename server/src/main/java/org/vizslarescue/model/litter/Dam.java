package org.vizslarescue.model.litter;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Dam {
    private String name;
    private String brs;

    private Date dob;
    private int total_litters;
    private int total_pups;
}
