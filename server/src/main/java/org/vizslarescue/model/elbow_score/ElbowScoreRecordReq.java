package org.vizslarescue.model.elbow_score;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class ElbowScoreRecordReq {
    @Pattern(regexp = "[A-Z][A-Z][0-9]")
    private String brs;
    private Date date;
    @PositiveOrZero
    private int score;
}
