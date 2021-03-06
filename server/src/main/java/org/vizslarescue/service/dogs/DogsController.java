package org.vizslarescue.service.dogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vizslarescue.Utils.GenericController;
import org.vizslarescue.Utils.ServiceMapperImpl;
import org.vizslarescue.model.dog.Dog;

@RestController
@RequestMapping(path = "/api/dogs")
public class DogsController extends GenericController<Dog> {

    @Autowired
    public DogsController(DogRepository repository, ServiceMapperImpl mapper)
    {
        super(repository, mapper);
    }
}
