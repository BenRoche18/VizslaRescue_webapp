package org.vizslarescue.model.breeder;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BreederReq {
    @NotBlank
    private String names;
}
