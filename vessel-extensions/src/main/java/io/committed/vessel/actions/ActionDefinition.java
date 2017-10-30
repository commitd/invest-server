package io.committed.vessel.actions;

public interface ActionDefinition {

  String action();

  // TODO: it would be nice to describe the payload POJO class -> Json shape?
  // eg
  // Object payload() {
  // return new Object() {
  // String id;
  //
  // Map<String,Object> attributes();
  // };
  // }

  String title();

  default String description() {
    return "";
  }

}
