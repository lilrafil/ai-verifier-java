package io.aiverifier.client.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Response from verifying an AI answer.
 */
public class VerifyAnswerResponse {

    @SerializedName("decision_id")
    private String decisionId;

    private VerificationStatus status;

    private double confidence;

    private List<String> reasons;

    @SerializedName("evidence_refs")
    private List<String> evidenceRefs;

    public String getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(String decisionId) {
        this.decisionId = decisionId;
    }

    public VerificationStatus getStatus() {
        return status;
    }

    public void setStatus(VerificationStatus status) {
        this.status = status;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public List<String> getEvidenceRefs() {
        return evidenceRefs;
    }

    public void setEvidenceRefs(List<String> evidenceRefs) {
        this.evidenceRefs = evidenceRefs;
    }
}
