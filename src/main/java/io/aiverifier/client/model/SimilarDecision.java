package io.aiverifier.client.model;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * A decision similar to the query.
 */
public class SimilarDecision {

    @SerializedName("decision_id")
    private String decisionId;

    private String question;

    @SerializedName("ai_answer")
    private String aiAnswer;

    private Map<String, Object> context;

    @SerializedName("similarity_score")
    private double similarityScore;

    @SerializedName("verification_status")
    private VerificationStatus verificationStatus;

    private long timestamp;

    public String getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(String decisionId) {
        this.decisionId = decisionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAiAnswer() {
        return aiAnswer;
    }

    public void setAiAnswer(String aiAnswer) {
        this.aiAnswer = aiAnswer;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(double similarityScore) {
        this.similarityScore = similarityScore;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
