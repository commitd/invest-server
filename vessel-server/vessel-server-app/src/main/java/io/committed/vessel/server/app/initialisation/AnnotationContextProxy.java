package io.committed.vessel.server.app.initialisation;

import java.util.function.Supplier;

public interface AnnotationContextProxy {

  <T> void registerBean(String name, Class<T> clazz, Supplier<T> supplier);

  void register(Class<?>... classes);

}
