package org.vizslarescue.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.vizslarescue.model.Dog;

public interface DogsRepository extends MongoRepository<Dog, String> {
}