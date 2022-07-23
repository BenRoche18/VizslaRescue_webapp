package org.vizslarescue.model.metadata;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class TextFieldDescription extends FieldDescription {
  private final boolean multiline;
  private String regex;
  private List<String> acceptedValues;

  public TextFieldDescription(String businessName, String technicalName, boolean multiline, Integer width) {
    super(businessName, technicalName, FieldType.TEXT, width);
    this.multiline = multiline;
  }

  public TextFieldDescription regex(String pattern)
  {
    this.regex = pattern;
    return this;
  }

  public TextFieldDescription acceptedValues(Enum[] values)
  {
    this.acceptedValues = Arrays.asList(values).stream().map(Enum::toString).collect(Collectors.toList());
    return this;
  }
}
