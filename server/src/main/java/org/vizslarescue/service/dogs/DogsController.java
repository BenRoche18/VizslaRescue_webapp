package org.vizslarescue.service.dogs;

import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/dog")
    public Dog addDog(
        @RequestBody DogReq req
    ) {
      return dogRepository.save(mapper.mapDogReq(req));
    }

    @GetMapping("/dog/{id}")
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

    @GetMapping("/dogs")
    public Page<Dog> getDogs(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size,
        @RequestParam(required = false, defaultValue = "") List<String> sortKeys,
        @RequestParam(required = false, defaultValue = "") List<String> filters

    ) {
      Pageable pager = PageRequest.of(page, size, Sort.by(mapper.mapSortKeys(sortKeys)));
      return dogRepository.findAll(pager);
        // List<AggregationOperation> operations = new ArrayList<AggregationOperation>();
        // operations.add(Dog.addFieldsOperation());

        // MatchOperation matchOperation = Utils.getMatchOperation(Utils.mapFilterKeys(filters));
        // if(matchOperation != null) {
        //     operations.add(matchOperation);
        // }

        // List<AggregationOperation> countOperations = new ArrayList<AggregationOperation>(operations);
        // countOperations.add(Aggregation.group().count().as("total"));
        // Count count = mongoTemplate.aggregate(Aggregation.newAggregation(countOperations), "Dogs", Count.class).getUniqueMappedResult();
    
        // SortOperation sortOperation = Utils.getSortOperation(Utils.mapSortKeys(sortKeys));
        // if(sortOperation != null) {
        //     operations.add(sortOperation);
        // }

        // operations.add(Aggregation.skip(Long.valueOf(pageSize * page)));
        // operations.add(Aggregation.limit(pageSize));

        // PageRequest pager = Utils.getPager(pageSize, page, Utils.mapSortKeys(sortKeys));

        // System.out.println(Aggregation.newAggregation(Dog.class, operations));

        // return new PageImpl<Dog>(mongoTemplate.aggregate(Aggregation.newAggregation(Dog.class, operations), Dog.class).getMappedResults(), pager, count != null ? count.getTotal() : 0);
    }

    // @PutMapping("/api/dog/{id}")
    // public Dog editDog(
    //     @PathVariable String id,
    //     @RequestBody(required = false) Dog dog
    // ) {
    //   Dog existingDog = getDog(id);

    //   dog.setId(existingDog.getId());

    //   mongoTemplate.save(dog);
    //   return dog;
    // }

    // @DeleteMapping("/api/dog/{id}")
    // public void deleteDog(
    //     @PathVariable String id
    // ) {
    //     Dog dog = getDog(id);

    //     mongoTemplate.remove(dog);
    // }
}
