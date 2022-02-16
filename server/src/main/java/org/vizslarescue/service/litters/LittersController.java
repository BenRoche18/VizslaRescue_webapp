// package org.vizslarescue.service.litters;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.mongodb.core.MongoTemplate;
// import org.springframework.data.mongodb.core.query.Query;
// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.server.ResponseStatusException;
// import org.vizslarescue.Utils.Utils;
// import org.vizslarescue.model.litter.Litter;

// import io.swagger.v3.oas.annotations.parameters.RequestBody;


// @RestController
// public class LittersController {

//     @Autowired
//     MongoTemplate mongoTemplate;

//     @GetMapping("/api/litters")
//     public Page<Litter> getLitters(
//         @RequestParam(defaultValue = "0") String page,
//         @RequestParam(defaultValue = "100") String pageSize,
//         @RequestParam(required = false, defaultValue = "") List<String> sortKeys,
//         @RequestParam(required = false, defaultValue = "") List<String> filters

//     ) {
//         PageRequest pager = Utils.getPager(Integer.parseInt(pageSize), Integer.parseInt(page), Utils.mapSortKeys(sortKeys));
//         Query query = Utils.getQuery(Utils.mapFilterKeys(filters));
        
//         query = query.with(pager);

//         return new PageImpl<Litter>(mongoTemplate.find(query, Litter.class), pager, mongoTemplate.count(query.skip(-1).limit(-1), Litter.class));
//     }

//     @GetMapping("/api/litter/{id}")
//     public Litter getLitter(
//         @PathVariable String id
//     ) {
//         Litter litter = mongoTemplate.findById(id, Litter.class);

//         if(litter == null) {
//             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find litter with provided name");
//         } else {
//             return litter;
//         }
//     }

//     @PostMapping("/api/litter")
//     public void addLitter(
//         @RequestBody(required = true) Litter litter
//     ) {
//       mongoTemplate.save(litter);
//     }

//     @PutMapping("/api/litter/{id}")
//     public Litter editLitter(
//         @PathVariable String id,
//         @RequestBody(required = false) Litter litter
//     ) {
//       Litter existingLitter = getLitter(id);

//       litter.setId(existingLitter.getId());

//       mongoTemplate.save(litter);
//       return litter;
//     }

//     @DeleteMapping("/api/litter/{id}")
//     public void deleteLitter(
//         @PathVariable String id
//     ) {
//         Litter litter = getLitter(id);

//         mongoTemplate.remove(litter);
//     }
// }
