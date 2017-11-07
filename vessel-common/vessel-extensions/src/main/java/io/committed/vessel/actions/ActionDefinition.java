package io.committed.vessel.actions;

public interface ActionDefinition {

  String getAction();

  // TODO: it would be nice to describe the payload POJO class -> Json shape?
  // Perhaps with https://github.com/vojtechhabarta/typescript-generator TypeScriptGenerator?
  // This would be something we could offer as GraphQL flow or typescript typeextension
  // eg
  // Object payload() {
  // return new Object() {
  // String id;
  //
  // Map<String,Object> attributes();
  // };
  // }

  default Class<?> getPayload() {
    return null;
  }

  String getTitle();

  default String getDescription() {
    return "";
  }

}
