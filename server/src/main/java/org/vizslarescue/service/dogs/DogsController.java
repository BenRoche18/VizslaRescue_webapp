package org.vizslarescue.service.dogs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
        return null;//repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("A dog with id '%s' was not found.", id)));
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
        @RequestParam(required = false) String name
    ) {
      Dog dog = getDog(id);

      if (name != null && !name.isBlank())
      {
          dog.setName(name);
      }

      //repo.save(dog);
      return dog;
    }

    @DeleteMapping("/api/dog/{id}")
    public void deleteDog(
        @PathVariable String id
    ) {
        //repo.deleteById(id);
    }
}
