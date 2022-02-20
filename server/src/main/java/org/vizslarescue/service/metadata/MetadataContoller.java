package org.vizslarescue.service.metadata;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vizslarescue.model.breeder.Breeder;
import org.vizslarescue.model.dog.Dog;
import org.vizslarescue.model.elbow_score.ElbowScoreRecord;
import org.vizslarescue.model.hip_score.HipScoreRecord;
import org.vizslarescue.model.litter.Litter;
import org.vizslarescue.model.metadata.EntityDescription;

@RequestMapping(path = "/api/metadata")
@RestController
public class MetadataContoller {

  private List<EntityDescription> entities = Arrays.asList(
    Breeder.getDescription(),
    Litter.getDescription(),
    Dog.getDescription(),
    ElbowScoreRecord.getDescription(),
    HipScoreRecord.getDescription()
  );

  @GetMapping("")
  public List<EntityDescription> get() {
    return this.entities;
  }
}
