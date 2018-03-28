package io.committed.invest.core.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Core settings for authentication */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationSettings {
  private boolean enabled = false;
}
