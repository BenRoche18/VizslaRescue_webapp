package org.vizslarescue.Utils;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface GenericRepository<T> extends PagingAndSortingRepository<T, Integer>, JpaSpecificationExecutor<T> {
}
