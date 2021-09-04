package org.vizslarescue.Utils;

import java.util.Arrays;

import org.springframework.data.mongodb.core.query.Criteria;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterKey {
    private String field;
    private Operator operator;
    private String value;

    public static enum Operator {
        EQUALS("equals"),
        CONTAINS("contains"),
        STARTS_WITH("startsWith"),
        ENDS_WITH("endsWith");

        public final String label;

        private Operator(String label) {
            this.label = label;
        }
    }

    public Criteria getCriteria() {
        Criteria criteria = Criteria.where(this.field);

        switch(this.operator) {
            case CONTAINS:
                criteria.regex(this.value, "i");
                break;
            case EQUALS:
                criteria.is(this.value);
                break;
            case STARTS_WITH:
                criteria.regex("^" + this.value, "i");
                break;
            case ENDS_WITH:
                criteria.regex(this.value + "$", "i");
                break;
        }
 
        return criteria;
    }

    // of form 'field' Operator 'value'
    public static FilterKey fromString(String string) {
        String[] parts = string.split(" ");

        return new FilterKey(parts[0], Operator.valueOf(parts[1]), String.join(" ", Arrays.copyOfRange(parts, 2, parts.length)).replace("'", ""));
    }
}