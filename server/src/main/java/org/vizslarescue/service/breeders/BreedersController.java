package org.vizslarescue.service.breeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vizslarescue.utils.GenericController;
import org.vizslarescue.utils.ServiceMapperImpl;
import org.vizslarescue.model.breeder.Breeder;

@RequestMapping(path = "/api/breeders")
@RestController
public class BreedersController extends GenericController<Breeder> {

    @Autowired
    public BreedersController(BreedersRepository repository, ServiceMapperImpl mapper)
    {
        super(repository, mapper);
    }
}
