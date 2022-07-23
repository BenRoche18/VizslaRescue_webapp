package org.vizslarescue.service.user;

import org.vizslarescue.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String>
{
  
}
