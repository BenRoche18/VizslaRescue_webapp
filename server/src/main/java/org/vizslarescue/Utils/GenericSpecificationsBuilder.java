package org.vizslarescue.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

public class GenericSpecificationsBuilder<T> {
   
   private final List<SearchCriteria> params;
   private final List<Specification<T>> specifications;
  
   public GenericSpecificationsBuilder() {
      this.params = new ArrayList<>();
      this.specifications = new ArrayList<>();
   }

   public final GenericSpecificationsBuilder<T> with(final String key, final SearchOperation searchOperation, final String arg) {
      return with(key, searchOperation, false, arg);
   }
  
   public final GenericSpecificationsBuilder<T> with(final String key, final SearchOperation searchOperation, final boolean isOrOperation, final String arg) {
      params.add(SearchCriteria.create(key, searchOperation, isOrOperation, arg));
      return this;
   }

   public final GenericSpecificationsBuilder<T> with(Specification<T> specification) {
      specifications.add(specification);
      return this;
   }

   public Specification<T> build() {
      Specification<T> result = null;
      if (!params.isEmpty()) {
         result = new GenericSpecification<>(params.get(0));
         for (int index = 1; index < params.size(); ++index) {
            SearchCriteria searchCriteria = params.get(index);
            result = searchCriteria.isOrOperation() 
            ? Specification.where(result).or(new GenericSpecification<>(searchCriteria))
            : Specification.where(result).and(new    GenericSpecification<>(searchCriteria));
         }
      }
      if (!specifications.isEmpty()) {
         int index = 0;
         if (Objects.isNull(result)) {
            result = specifications.get(index++);
         }
         for (; index < specifications.size(); ++index) {
            result =    Specification.where(result).and(specifications.get(index));
         }
      }
      return result;
   }
}