package org.vizslarescue.service.breeders;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.vizslarescue.model.breeder.Breeder;

@Repository
public interface BreedersRepository extends PagingAndSortingRepository<Breeder, Integer>, JpaSpecificationExecutor<Breeder> {
    
}
