package org.vizslarescue.service.dogs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
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
import org.vizslarescue.model.Dog;


@RestController
public class DogsController {

    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("/api/dogs")
    public Page<Dog> getDogs(
        @RequestParam(defaultValue = "0") String page,
        @RequestParam(defaultValue = "100") String pageSize,
        @RequestParam(required = false) List<String> sortKeys,
        @RequestParam(required = false) List<String> filters

    ) {
        PageRequest pager = Utils.getPager(Integer.parseInt(pageSize), Integer.parseInt(page), sortKeys);
        Query query = Utils.getQuery(filters);
        
        query = query.with(pager);

        return new PageImpl<Dog>(mongoTemplate.find(query, Dog.class), pager, mongoTemplate.count(query.skip(-1).limit(-1), Dog.class));
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
        @RequestParam String name
    ) {
      Dog dog = new Dog();
      dog.setName(name);

      //repo.save(dog);
    }

    @PutMapping("/api/dog/{id}")
    public Dog editDog(
        @PathVariable String id,
        @RequestBody(required = false) Dog reqDog
    ) {
      Dog dog = getDog(id);

      reqDog.setId(dog.getId());

      mongoTemplate.save(reqDog);
      return reqDog;
    }

    @DeleteMapping("/api/dog/{id}")
    public void deleteDog(
        @PathVariable String id
    ) {
        Dog dog = getDog(id);

        mongoTemplate.remove(dog);
    }
}
