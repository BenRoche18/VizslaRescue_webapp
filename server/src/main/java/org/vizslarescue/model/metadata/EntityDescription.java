package org.vizslarescue.model.metadata;

import java.util.List;

import lombok.Data;

@Data
public class EntityDescription {
  private String technicalName;
  private String businessName;
  private String icon;
  private String alternativeKey;

  private List<FieldDescription> fields;
}
