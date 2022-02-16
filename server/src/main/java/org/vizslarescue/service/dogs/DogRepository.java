package org.vizslarescue.service.dogs;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.vizslarescue.model.dog.Dog;

public interface DogRepository extends PagingAndSortingRepository<Dog, Integer> {   
}
