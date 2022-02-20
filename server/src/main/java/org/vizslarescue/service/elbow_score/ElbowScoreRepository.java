package org.vizslarescue.service.elbow_score;

import org.springframework.stereotype.Repository;
import org.vizslarescue.Utils.GenericRepository;
import org.vizslarescue.model.elbow_score.ElbowScoreRecord;

@Repository
public interface ElbowScoreRepository extends GenericRepository<ElbowScoreRecord> {
}
