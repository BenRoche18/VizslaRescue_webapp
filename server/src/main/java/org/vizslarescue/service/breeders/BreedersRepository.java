package org.vizslarescue.service.breeders;

import org.springframework.stereotype.Repository;
import org.vizslarescue.utils.GenericRepository;
import org.vizslarescue.model.breeder.Breeder;

@Repository
public interface BreedersRepository extends GenericRepository<Breeder> {
}
