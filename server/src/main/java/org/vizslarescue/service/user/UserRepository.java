package org.vizslarescue.service.user;

import org.vizslarescue.model.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String>
{
  
}
