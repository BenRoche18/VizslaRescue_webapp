package org.vizslarescue.service.litters;

import org.springframework.stereotype.Repository;
import org.vizslarescue.Utils.GenericRepository;
import org.vizslarescue.model.litter.Litter;

@Repository
public interface LittersRepository extends GenericRepository<Litter> {   
    
}
