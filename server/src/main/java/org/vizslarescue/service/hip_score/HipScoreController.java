package org.vizslarescue.service.hip_score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vizslarescue.Utils.GenericController;
import org.vizslarescue.Utils.ServiceMapperImpl;
import org.vizslarescue.model.hip_score.HipScoreRecord;

@RequestMapping(path = "/api/hip_scores")
@RestController
public class HipScoreController extends GenericController<HipScoreRecord> {

    @Autowired
    public HipScoreController(HipScoreRepository repository, ServiceMapperImpl mapper)
    {
        super(repository, mapper);
    }
}
