package io.committed.invest.plugin.server.auth.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A user suitable for dto (transfer to client).
 *
 * <p>Does not provide any sensitive data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private String username;
  private String name;
  private Set<String> roles;
}
