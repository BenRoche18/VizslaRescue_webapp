package org.vizslarescue.Utils;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.vizslarescue.model.dog.Dog;
import org.vizslarescue.model.dog.DogReq;

@Mapper(componentModel = "spring")
public abstract class ServiceMapper{

  public abstract Dog mapDogReq(DogReq req);

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
}