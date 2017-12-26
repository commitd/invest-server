package io.committed.invest.extensions;

import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

@Service
public interface InvestExtension {

  default String getId() {
    // Use getUserClass so that we don't need to worry about if Spring has proxied the class (eg its
    // a config bean)
    return ClassUtils.getUserClass(this.getClass()).getSimpleName().toLowerCase();
  }

  default String getName() {
    return ClassUtils.getUserClass(this.getClass()).getSimpleName();
  }

  default String getDescription() {
    return "";
  }

}
