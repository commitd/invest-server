package io.committed.vessel.core.dto.auth;

import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VesselUser {
  @NotBlank
  @JsonProperty("username")
  private String username;

  @JsonProperty("authenticated")
  private boolean authenticated;

  @JsonProperty("roles")
  private Set<String> roles;
}
