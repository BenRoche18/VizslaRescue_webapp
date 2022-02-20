package org.vizslarescue.service.dogs;

import org.springframework.stereotype.Repository;
import org.vizslarescue.Utils.GenericRepository;
import org.vizslarescue.model.dog.Dog;

@Repository
public interface DogRepository extends GenericRepository<Dog> {   
}
