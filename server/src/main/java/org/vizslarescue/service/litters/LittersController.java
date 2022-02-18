package org.vizslarescue.service.litters;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.vizslarescue.Utils.ServiceMapperImpl;
import org.vizslarescue.model.litter.Litter;
import org.vizslarescue.model.litter.LitterReq;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping(path = "/api")
public class LittersController {

    @Autowired
    LittersRepository littersRepository;

    @Autowired
    private ServiceMapperImpl mapper;

    @PostMapping("/litters")
    public Litter addLitter(
        @Valid @RequestBody LitterReq req
    ) {
        return littersRepository.save(mapper.mapLitterReq(req));
    }

    @GetMapping("/litters/{id}")
    public Litter getLitter(
        @PathVariable Integer id
    ) {
        Optional<Litter> litter = littersRepository.findById(id);

        if(!litter.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find litter with provided id");
        } else {
            return litter.get();
        }
    }

    @GetMapping("/litters")
    public Page<Litter> getLitters(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size,
        @RequestParam(required = false, defaultValue = "") List<String> sortKeys,
        @RequestParam(required = false, defaultValue = "") List<String> filters

    ) {
      Specification<Litter> specification = mapper.mapFilterKeys(filters);
      Pageable pager = PageRequest.of(page, size, Sort.by(mapper.mapSortKeys(sortKeys)));
      return littersRepository.findAll(specification, pager);
    }
}
