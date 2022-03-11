package org.vizslarescue.Utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GenericSpecification<T> implements Specification<T> {
    private SearchCriteria searchCriteria;
   
    public GenericSpecification(final SearchCriteria searchCriteria){
        super();
        this.searchCriteria = searchCriteria;
    }
    
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        String arg = searchCriteria.getArg();
        String key = searchCriteria.getKey();
 
        switch (searchCriteria.getSearchOperation()) {
            case EQUALS:
            case EQ:
                return criteriaBuilder.equal(root.get(key), arg);
            case CONTAINS:
                return criteriaBuilder.like(root.get(key).as(String.class), "%" + arg + "%");
            case STARTS_WITH:
                return criteriaBuilder.like(root.get(key).as(String.class), arg + "%");
            case ENDS_WITH:
                return criteriaBuilder.like(root.get(key).as(String.class), "%" + arg);
            case NE:
                return criteriaBuilder.notEqual(root.get(key), arg);
            case GT:
                return criteriaBuilder.greaterThan(root.get(key), arg);
            case LT:
                return criteriaBuilder.lessThan(root.get(key), arg);
            case GTE:
                return criteriaBuilder.greaterThanOrEqualTo(root.get(key), arg);
            case LTE:
                return criteriaBuilder.lessThanOrEqualTo(root.get(key), arg);
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filter operator not supported (" + searchCriteria.getSearchOperation().toString() + ")");
        }
    }

    public static <T> GenericSpecification<T> create(SearchCriteria criteria)
    {
        return new GenericSpecification<T>(criteria);
    }
}
