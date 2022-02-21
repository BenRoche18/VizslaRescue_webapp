package org.vizslarescue.model.metadata;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FieldDescription {
  protected final String businessName;
  protected final String technicalName;
  protected final FieldType type;
  protected final Integer width;
}
