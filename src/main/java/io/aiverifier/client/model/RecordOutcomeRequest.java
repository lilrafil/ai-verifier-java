package io.aiverifier.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Request to record the outcome of a verification decision.
 */
public class RecordOutcomeRequest {

    @SerializedName("decision_id")
    private String decisionId;

    private OutcomeType outcome;

    private String notes;

    public RecordOutcomeRequest() {
    }

    public RecordOutcomeRequest(String decisionId, OutcomeType outcome, String notes) {
        this.decisionId = decisionId;
        this.outcome = outcome;
        this.notes = notes != null ? notes : "";
    }

    public String getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(String decisionId) {
        this.decisionId = decisionId;
    }

    public OutcomeType getOutcome() {
        return outcome;
    }

    public void setOutcome(OutcomeType outcome) {
        this.outcome = outcome;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
