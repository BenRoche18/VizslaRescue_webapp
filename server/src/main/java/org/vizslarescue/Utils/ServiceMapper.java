package org.vizslarescue.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.vizslarescue.model.elbow_score.ElbowScoreRecord;
import org.vizslarescue.model.hip_score.HipScoreRecord;

@Mapper(componentModel = "spring")
public abstract class ServiceMapper {

  public abstract HipScoreRecord mapHipScoreRecord(HipScoreRecord req);

  public abstract ElbowScoreRecord mapElbowScoreRecord(ElbowScoreRecord req);

  public List<Order> mapSortKeys(List<String> sortKeys)
  {
    return sortKeys.stream().map((it) -> {
      String[] parts = it.split(";");
      if(parts.length == 1 || parts[1].equals("asc"))
      {
        return Order.asc(parts[0]);
      }
      else if(parts[1].equals("desc"))
      {
        return Order.desc(parts[0]);
      }
      else
      {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The provided sort direction (" + parts[1] + ") must be one of [asc, desc]");
      }
    }).collect(Collectors.toList());
  }

  public <T> Specification<T> mapFilterKeys(List<String> filterKeys)
  {
    GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<T>();

    for(String filter : filterKeys)
    {
      String[] parts = filter.split(" ");
      builder.with(parts[0], mapSearchOperation(parts[1]), parts[2]);
    }

    return builder.build();
  }

  public SearchOperation mapSearchOperation(String operation)
  {
    switch(operation) {
      case "equals":
        return SearchOperation.EQUALS;
      case "contains":
        return SearchOperation.CONTAINS;
      case "startsWith":
        return SearchOperation.STARTS_WITH;
      case "endsWith":
        return SearchOperation.ENDS_WITH;
      case "=":
        return SearchOperation.EQ;
      case "!=":
        return SearchOperation.NE;
      case ">":
        return SearchOperation.GT;
      case "<":
        return SearchOperation.LT;
      case ">=":
        return SearchOperation.GTE;
      case "<=":
        return SearchOperation.LTE;
      default:
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unrecognised filter operator (" + operation + ")");
    }
  }
}