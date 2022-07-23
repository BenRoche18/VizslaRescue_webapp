package org.vizslarescue.service.user;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.vizslarescue.model.user.User;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

import org.vizslarescue.model.user.Role;

@Service
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/user")
public class UserController implements UserDetailsService
{
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Secured("ROLE_USER")
  @GetMapping("")
  public @ResponseBody User getUser(Authentication authentication)
  {
    if(authentication == null)
    {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
    User user = new User();
    user.setUsername(authentication.getName());
    user.setRoles(authentication.getAuthorities().stream().map(auth -> {
      Role role = new Role();
      role.setRole(auth.getAuthority());
      return role;
    }).collect(Collectors.toSet()));
    return user;
  }

  @Secured("ROLE_ADMIN")
  @PostMapping("")
  public @ResponseBody User saveUser(
      @RequestBody User user
  ) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setActive(true);
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findById(username)
        .orElseThrow(() -> new UsernameNotFoundException("Provided username (" + username + ") could not be found"));
      List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
      return buildUserForAuthentication(user, authorities);
  }

  private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
    return userRoles.stream()
      .map(role -> new SimpleGrantedAuthority(role.getRole()))
      .collect(Collectors.toList());
  }

  private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
    return new org.springframework.security.core.userdetails.User(
      user.getUsername(), 
      user.getPassword(),
      user.getActive(),
      true, 
      true, 
      true, 
      authorities
    );
  }
}
