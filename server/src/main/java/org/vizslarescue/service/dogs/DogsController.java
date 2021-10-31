package org.vizslarescue.service.dogs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.vizslarescue.Utils.Utils;
import org.vizslarescue.model.count.Count;
import org.vizslarescue.model.dog.Dog;


@RestController
public class DogsController {

    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("/api/dogs")
    public Page<Dog> getDogs(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int pageSize,
        @RequestParam(required = false, defaultValue = "")List<String> sortKeys,
        @RequestParam(required = false, defaultValue = "") List<String> filters

    ) {
        List<AggregationOperation> operations = new ArrayList<AggregationOperation>();
        operations.add(Dog.addFieldsOperation());

        MatchOperation matchOperation = Utils.getMatchOperation(Utils.mapFilterKeys(filters));
        if(matchOperation != null) {
            operations.add(matchOperation);
        }

        List<AggregationOperation> countOperations = new ArrayList<AggregationOperation>(operations);
        countOperations.add(Aggregation.group().count().as("total"));
        Count count = mongoTemplate.aggregate(Aggregation.newAggregation(countOperations), "Dogs", Count.class).getUniqueMappedResult();
    
        SortOperation sortOperation = Utils.getSortOperation(Utils.mapSortKeys(sortKeys));
        if(sortOperation != null) {
            operations.add(sortOperation);
        }

        operations.add(Aggregation.skip(Long.valueOf(pageSize * page)));
        operations.add(Aggregation.limit(pageSize));

        PageRequest pager = Utils.getPager(pageSize, page, Utils.mapSortKeys(sortKeys));

        System.out.println(Aggregation.newAggregation(Dog.class, operations));

        return new PageImpl<Dog>(mongoTemplate.aggregate(Aggregation.newAggregation(Dog.class, operations), Dog.class).getMappedResults(), pager, count != null ? count.getTotal() : 0);
    }

    @GetMapping("/api/dog/{id}")
    public Dog getDog(
        @PathVariable String id
    ) {
        Dog dog = mongoTemplate.findById(id, Dog.class);

        if(dog == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find dog with provided id");
        } else {
            return dog;
        }
    }

    @PostMapping("/api/dog")
    public void addDog(
        @RequestBody(required = true) Dog dog
    ) {
      mongoTemplate.save(dog);
    }

    @PutMapping("/api/dog/{id}")
    public Dog editDog(
        @PathVariable String id,
        @RequestBody(required = false) Dog dog
    ) {
      Dog existingDog = getDog(id);

      dog.setId(existingDog.getId());

      mongoTemplate.save(dog);
      return dog;
    }

    @DeleteMapping("/api/dog/{id}")
    public void deleteDog(
        @PathVariable String id
    ) {
        Dog dog = getDog(id);

        mongoTemplate.remove(dog);
    }
}
