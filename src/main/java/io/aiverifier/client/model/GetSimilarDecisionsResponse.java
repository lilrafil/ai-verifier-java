package io.aiverifier.client.model;

import java.util.List;

/**
 * Response containing similar decisions.
 */
public class GetSimilarDecisionsResponse {

    private List<SimilarDecision> decisions;

    public List<SimilarDecision> getDecisions() {
        return decisions;
    }

    public void setDecisions(List<SimilarDecision> decisions) {
        this.decisions = decisions;
    }
}
