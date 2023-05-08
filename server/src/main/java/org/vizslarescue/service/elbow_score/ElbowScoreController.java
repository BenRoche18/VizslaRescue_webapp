package org.vizslarescue.service.elbow_score;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vizslarescue.utils.GenericController;
import org.vizslarescue.utils.ServiceMapperImpl;
import org.vizslarescue.model.elbow_score.ElbowScoreRecord;

@RequestMapping(path = "/api/elbow_scores")
@RestController
public class ElbowScoreController extends GenericController<ElbowScoreRecord> {
    public ElbowScoreController(ElbowScoreRepository repository, ServiceMapperImpl mapper)
    {
        super(repository, mapper);
    }
}
