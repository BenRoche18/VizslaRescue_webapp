package org.vizslarescue.model.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
  @Id
  @Length(min = 5, message = "*Your user name must have at least 5 characters")
  @NotEmpty(message = "*Please provide a user name")
  private String username;

  @Length(min = 5, message = "*Your password must have at least 5 characters")
  @NotEmpty(message = "*Please provide your password")
  private String password;

  private Boolean active;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
    name = "user_role", 
    joinColumns = @JoinColumn(name="username"),
    inverseJoinColumns = @JoinColumn(name="role")
  )
  private Set<Role> roles;
}
