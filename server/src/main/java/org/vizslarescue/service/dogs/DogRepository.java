package org.vizslarescue.service.dogs;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.vizslarescue.model.dog.Dog;

@Repository
public interface DogRepository extends PagingAndSortingRepository<Dog, Integer>, JpaSpecificationExecutor<Dog> {   
}
