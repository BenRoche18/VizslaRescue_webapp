package org.vizslarescue.service.hip_score;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vizslarescue.utils.GenericController;
import org.vizslarescue.utils.ServiceMapperImpl;
import org.vizslarescue.model.hip_score.HipScoreRecord;

@RequestMapping(path = "/api/hip_scores")
@RestController
public class HipScoreController extends GenericController<HipScoreRecord> {
    public HipScoreController(HipScoreRepository repository, ServiceMapperImpl mapper)
    {
        super(repository, mapper);
    }
}
