package org.vizslarescue.Utils;

import org.springframework.data.domain.Sort.Direction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortKey {
    private String field;
    private Direction direction;

    // of form field;direction
    public static SortKey fromString(String string) {
        String[] parts = string.split(";");
        Direction direction = (parts.length == 1 || parts[1].equals("asc")) ? Direction.ASC : Direction.DESC;

        return new SortKey(parts[0], direction);
    }
}