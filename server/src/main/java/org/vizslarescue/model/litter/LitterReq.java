package org.vizslarescue.model.litter;

import java.util.Date;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class LitterReq {
    @Pattern(regexp = "[A-Z][A-Z][0-9]")
    private String brs;
    private Date date;
    private boolean wasCesarean;
}
