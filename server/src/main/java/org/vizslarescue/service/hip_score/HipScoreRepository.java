package org.vizslarescue.service.hip_score;

import org.springframework.stereotype.Repository;
import org.vizslarescue.Utils.GenericRepository;
import org.vizslarescue.model.hip_score.HipScoreRecord;

@Repository
public interface HipScoreRepository extends GenericRepository<HipScoreRecord> {
}
