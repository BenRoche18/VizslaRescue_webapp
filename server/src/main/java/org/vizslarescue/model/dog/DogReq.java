package org.vizslarescue.model.dog;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DogReq {
    @NotBlank
    private String name;
    @NotNull
    private Gender gender;
    private String additionalDetails;
}
