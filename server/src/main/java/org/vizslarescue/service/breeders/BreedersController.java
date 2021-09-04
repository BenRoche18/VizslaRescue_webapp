package org.vizslarescue.service.breeders;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.vizslarescue.Utils.Utils;
import org.vizslarescue.model.breeder.Breeder;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
public class BreedersController {

    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("/api/breeders")
    public Page<Breeder> getBreeders(
        @RequestParam(defaultValue = "0") String page,
        @RequestParam(defaultValue = "100") String pageSize,
        @RequestParam(required = false, defaultValue = "") List<String> sortKeys,
        @RequestParam(required = false, defaultValue = "") List<String> filters

    ) {
        PageRequest pager = Utils.getPager(Integer.parseInt(pageSize), Integer.parseInt(page), Utils.mapSortKeys(sortKeys));
        Query query = Utils.getQuery(Utils.mapFilterKeys(filters));
        
        query = query.with(pager);

        return new PageImpl<Breeder>(mongoTemplate.find(query, Breeder.class), pager, mongoTemplate.count(query.skip(-1).limit(-1), Breeder.class));
    }

    @GetMapping("/api/breeder/{id}")
    public Breeder getBreeder(
        @PathVariable String id
    ) {
        Breeder breeder = mongoTemplate.findById(id, Breeder.class);

        if(breeder == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find breeder with provided name");
        } else {
            return breeder;
        }
    }

    @PostMapping("/api/breeder")
    public void addBreeder(
        @RequestBody(required = true) Breeder breeder
    ) {
      mongoTemplate.save(breeder);
    }

    @PutMapping("/api/breeder/{id}")
    public Breeder editBreeder(
        @PathVariable String id,
        @RequestBody(required = false) Breeder breeder
    ) {
      Breeder existingBreeder = getBreeder(id);

      breeder.setId(existingBreeder.getId());

      mongoTemplate.save(breeder);
      return breeder;
    }

    @DeleteMapping("/api/breeder/{id}")
    public void deleteBreeder(
        @PathVariable String id
    ) {
        Breeder breeder = getBreeder(id);

        mongoTemplate.remove(breeder);
    }
}
