package org.vizslarescue.Utils;

import java.util.Arrays;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterKey {
    private String field;
    private Operator operator;
    private String value;

    public static enum Operator {
        EQUALS,
        CONTAINS,
        STARTS_WITH,
        ENDS_WITH,
        EQ,
        NE,
        GT,
        LT,
        GTE,
        LTE;
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
            case EQ:
                criteria.is(Integer.parseInt(this.value));
                break;
            case NE:
                criteria.ne(Integer.parseInt(this.value));
                break;
            case GT:
                criteria.gt(Integer.parseInt(this.value));
                break;
            case LT:
                criteria.lt(Integer.parseInt(this.value));
                break;
            case GTE:
                criteria.gte(Integer.parseInt(this.value));
                break;
            case LTE:
                criteria.lte(Integer.parseInt(this.value));
                break;
        }

        return criteria;
    }

    // of form 'field' Operator 'value'
    public static FilterKey fromString(String string) {
        String[] parts = string.split(" ");
        Operator operator;

        switch(parts[1]) {
            case "equals":
              operator = Operator.EQUALS;
              break;
            case "contains":
              operator = Operator.CONTAINS;
              break;
            case "startsWith":
              operator = Operator.STARTS_WITH;
              break;
            case "endsWith":
              operator = Operator.ENDS_WITH;
              break;
            case "=":
              operator = Operator.EQ;
              break;
            case "!=":
              operator = Operator.NE;
              break;
            case ">":
              operator = Operator.GT;
              break;
            case "<":
              operator = Operator.LT;
              break;
            case ">=":
              operator = Operator.GTE;
              break;
            case "<=":
              operator = Operator.LTE;
              break;
            default:
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filter operator");
        }

        return new FilterKey(parts[0], operator, String.join(" ", Arrays.copyOfRange(parts, 2, parts.length)).replace("'", ""));
    }
}