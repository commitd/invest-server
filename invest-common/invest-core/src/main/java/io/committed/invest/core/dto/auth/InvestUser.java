package io.committed.invest.core.dto.auth;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user within Invest.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestUser {
  @NotBlank
  @JsonProperty("username")
  private String username;

  @JsonProperty("authenticated")
  private boolean authenticated;

  @JsonProperty("roles")
  private Set<String> roles;
}
