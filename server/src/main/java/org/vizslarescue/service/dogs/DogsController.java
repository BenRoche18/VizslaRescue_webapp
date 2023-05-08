package org.vizslarescue.service.dogs;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vizslarescue.utils.GenericController;
import org.vizslarescue.utils.ServiceMapperImpl;
import org.vizslarescue.model.dog.Dog;

@RestController
@RequestMapping(path = "/api/dogs")
public class DogsController extends GenericController<Dog> {
    public DogsController(DogRepository repository, ServiceMapperImpl mapper)
    {
        super(repository, mapper);
    }
}
