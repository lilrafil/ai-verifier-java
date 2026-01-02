package io.aiverifier.client.model;

import java.util.Map;

/**
 * Request to get similar past decisions.
 */
public class GetSimilarDecisionsRequest {

    private String question;
    private Map<String, Object> context;
    private int limit;

    public GetSimilarDecisionsRequest() {
    }

    public GetSimilarDecisionsRequest(String question, Map<String, Object> context, int limit) {
        this.question = question;
        this.context = context;
        this.limit = Math.max(1, Math.min(limit, 100)); // Clamp between 1-100
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
