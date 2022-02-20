package org.vizslarescue.model.metadata;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class TextFieldDescription extends FieldDescription {
  private final boolean isMultiLine;
  private String regex;
  private List<String> acceptedValues;

  public TextFieldDescription(String businessName, String technicalName, boolean isMultiLine) {
    super(businessName, technicalName, FieldType.TEXT);
    this.isMultiLine = isMultiLine;
  }

  public TextFieldDescription regex(String pattern)
  {
    this.regex = pattern;
    return this;
  }

  public <T extends Enum> TextFieldDescription acceptedValues(T[] values)
  {
    this.acceptedValues = Arrays.asList(values).stream().map(Enum::toString).collect(Collectors.toList());
    return this;
  }
}
