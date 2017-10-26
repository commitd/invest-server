package io.committed.vessel.plugin.server.auth.dao;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class UserAccount {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private String username;
  private String password;
  private String name;

  // Holder to divide the users into 'teams' but may not used for authentication (is username should
  // be unique over all organisations)
  private String organisation;

  private boolean enabled;
  private boolean expired;
  private boolean locked;
  private boolean passwordExpired;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> authorities;

  public UserAccount(final String username, final String password, final String name,
      final String organisation,
      final Set<String> authorities) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.organisation = organisation;
    this.authorities = authorities;

    enabled = true;
    expired = false;
    locked = false;
    passwordExpired = false;
  }

  public boolean hasAuthority(final String authority) {
    return authorities != null && authorities.contains(authority);
  }
}
