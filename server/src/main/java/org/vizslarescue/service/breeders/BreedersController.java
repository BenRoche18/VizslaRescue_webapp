package org.vizslarescue.service.breeders;

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
import org.vizslarescue.model.breeder.Breeder;
import org.vizslarescue.model.breeder.BreederReq;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping(path = "/api")
public class BreedersController {

    @Autowired
    BreedersRepository breedersRepository;

    @Autowired
    private ServiceMapperImpl mapper;

    @PostMapping("/breeders")
    public Breeder addBreeder(
        @Valid @RequestBody BreederReq req
    ) {
        return breedersRepository.save(mapper.mapBreederReq(req));
    }

    @GetMapping("/breeders/{id}")
    public Breeder getBreeder(
        @PathVariable Integer id
    ) {
        Optional<Breeder> breeder = breedersRepository.findById(id);

        if(!breeder.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find breeder with provided id");
        } else {
            return breeder.get();
        }
    }

    @GetMapping("/breeders")
    public Page<Breeder> getBreeders(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size,
        @RequestParam(required = false, defaultValue = "") List<String> sortKeys,
        @RequestParam(required = false, defaultValue = "") List<String> filters

    ) {
      Specification<Breeder> specification = mapper.mapFilterKeys(filters);
      Pageable pager = PageRequest.of(page, size, Sort.by(mapper.mapSortKeys(sortKeys)));
      return breedersRepository.findAll(specification, pager);
    }
}
