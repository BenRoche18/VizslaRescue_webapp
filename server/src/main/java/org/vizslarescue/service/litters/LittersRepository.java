package org.vizslarescue.service.litters;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.vizslarescue.model.litter.Litter;

@Repository
public interface LittersRepository extends PagingAndSortingRepository<Litter, Integer>, JpaSpecificationExecutor<Litter> {   
    
}
