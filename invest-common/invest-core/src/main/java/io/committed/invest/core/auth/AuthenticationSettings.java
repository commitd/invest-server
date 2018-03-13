package io.committed.invest.core.auth;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Core settings for authentication
 */
@Data
@AllArgsConstructor
public class AuthenticationSettings {
  private boolean enabled;
}
