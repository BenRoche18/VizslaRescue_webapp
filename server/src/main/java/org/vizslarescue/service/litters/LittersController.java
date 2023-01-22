package org.vizslarescue.service.litters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vizslarescue.utils.GenericController;
import org.vizslarescue.utils.ServiceMapperImpl;
import org.vizslarescue.model.litter.Litter;

@RestController
@RequestMapping(path = "/api/litters")
public class LittersController extends GenericController<Litter> {

    @Autowired
    public LittersController(LittersRepository repository, ServiceMapperImpl mapper)
    {
        super(repository, mapper);
    }
}
