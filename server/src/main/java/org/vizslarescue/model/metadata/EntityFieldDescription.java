package org.vizslarescue.model.metadata;

import lombok.Data;

@Data
public class EntityFieldDescription extends FieldDescription {
  private final String entity;

  public EntityFieldDescription(String businessName, String technicalName, Integer width, String entity) {
    super(businessName, technicalName, FieldType.ENTITY, width);
    this.entity = entity;
  }
}
