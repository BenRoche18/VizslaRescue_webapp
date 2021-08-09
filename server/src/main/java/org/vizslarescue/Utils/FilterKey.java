package org.vizslarescue.Utils;

import java.util.Arrays;

import org.springframework.data.mongodb.core.query.Criteria;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterKey {
    private String field;
    private String operator;
    private String value;

    public Criteria getCriteria() {
        Criteria criteria = Criteria.where(this.field);

        switch(this.operator) {
            case "contains":
                criteria.regex(this.value, "i");
                break;
            case "equals":
                criteria.is(this.value);
                break;
            case "startsWith":
                criteria.regex("^" + this.value, "i");
                break;
            case "endsWith":
                criteria.regex(this.value + "$", "i");
                break;
        }
 
        return criteria;
    }

    // of form field operation 'value'
    public static FilterKey fromString(String string) {
        String[] parts = string.split(" ");

        return new FilterKey(parts[0], parts[1], String.join(" ", Arrays.copyOfRange(parts, 2, parts.length)).replace("'", ""));
    }
}