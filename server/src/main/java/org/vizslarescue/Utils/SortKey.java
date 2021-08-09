package org.vizslarescue.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortKey {
    private String field;
    private String direction;

    // of form field;direction
    public static SortKey fromString(String string) {
        String[] parts = string.split(";");

        if(parts.length == 1) {
            return new SortKey(parts[0], "asc");
        } else {
            return new SortKey(parts[0], parts[1]);
        }
    }
}