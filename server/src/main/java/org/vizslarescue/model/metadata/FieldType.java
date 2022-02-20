package org.vizslarescue.model.metadata;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FieldType {
  ID("id"),
  TEXT("text"),
  NUMBER("number"),
  DATE("date"),
  BOOLEAN("boolean"),
  ENTITY("entity");

  public final String value;

  FieldType(String value) {
    this.value = value;
  }

  @JsonValue
  public String toValue() {
    return this.value;
  }
}
