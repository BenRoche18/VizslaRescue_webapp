package org.vizslarescue.service.elbow_score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vizslarescue.Utils.GenericController;
import org.vizslarescue.Utils.ServiceMapperImpl;
import org.vizslarescue.model.elbow_score.ElbowScoreRecord;

@RequestMapping(path = "/api/elbow_scores")
@RestController
public class ElbowScoreController extends GenericController<ElbowScoreRecord> {

    @Autowired
    public ElbowScoreController(ElbowScoreRepository repository, ServiceMapperImpl mapper)
    {
        super(repository, mapper);
    }
}
