package org.vizslarescue.Utils;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

public abstract class GenericController<T extends GenericEntity> {

    protected final GenericRepository<T> repository;
    protected final ServiceMapperImpl mapper;

    public GenericController(GenericRepository<T> repository, ServiceMapperImpl mapper)
    {
        this.repository = repository;
        this.mapper = mapper;
    }

    @PostMapping("")
    public T add(
        @Valid @RequestBody T req
    ) {
        return repository.save(req);
    }

    @GetMapping("/{id}")
    public T get(
        @PathVariable Integer id
    ) {
        Optional<T> obj = repository.findById(id);

        if(!obj.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find with provided id");
        } else {
            return obj.get();
        }
    }

    @PutMapping("/{id}")
    public T edit(
        @PathVariable Integer id,
        @RequestBody T req
    ) {
        T obj = get(id);
        req.setId(id);
        return repository.save(req);
    }

    @DeleteMapping("/{id}")
    public void delete(
        @PathVariable Integer id
    ) {
        repository.deleteById(id);
    }

    @GetMapping("")
    public Page<T> get(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size,
        @RequestParam(required = false, defaultValue = "") List<String> sortKeys,
        @RequestParam(required = false, defaultValue = "") List<String> filters

    ) {
      Specification<T> specification = mapper.mapFilterKeys(filters);
      Pageable pager = PageRequest.of(page, size, Sort.by(mapper.mapSortKeys(sortKeys)));
      return repository.findAll(specification, pager);
    }
}
