package org.vizslarescue.service.dogs;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.vizslarescue.Utils.ServiceMapperImpl;
import org.vizslarescue.model.dog.Dog;
import org.vizslarescue.model.dog.DogReq;

@RestController
@RequestMapping(path = "/api")
public class DogsController {

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private ServiceMapperImpl mapper;

    @PostMapping("/dogs")
    public Dog addDog(
        @Valid @RequestBody DogReq req
    ) {
      return dogRepository.save(mapper.mapDogReq(req));
    }

    @GetMapping("/dogs/{id}")
    public Dog getDog(
        @PathVariable Integer id
    ) {
        Optional<Dog> dog = dogRepository.findById(id);

        if(!dog.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find dog with provided id");
        } else {
            return dog.get();
        }
    }

    @PutMapping("/dogs/{id}")
    public Dog editDog(
        @PathVariable Integer id,
        @RequestBody DogReq req
    ) {
        Dog dog = getDog(id);

        if(req.getName() != null)
        {
            dog.setName(req.getName());
        }
        if(req.getGender() != null)
        {
            dog.setGender(req.getGender());
        }
        if(req.getAdditionalDetails() != null)
        {
            dog.setAdditionalDetails(req.getAdditionalDetails());
        }

        return dogRepository.save(dog);
    }

    @DeleteMapping("/dogs/{id}")
    public void deleteDog(
        @PathVariable Integer id
    ) {
        dogRepository.deleteById(id);
    }

    @GetMapping("/dogs")
    public Page<Dog> getDogs(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size,
        @RequestParam(required = false, defaultValue = "") List<String> sortKeys,
        @RequestParam(required = false, defaultValue = "") List<String> filters

    ) {
      Specification<Dog> specification = mapper.mapFilterKeys(filters);
      Pageable pager = PageRequest.of(page, size, Sort.by(mapper.mapSortKeys(sortKeys)));
      return dogRepository.findAll(specification, pager);
    }
}
