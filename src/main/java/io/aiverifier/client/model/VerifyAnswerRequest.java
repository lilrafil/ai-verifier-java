package io.aiverifier.client.model;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Request to verify an AI-generated answer.
 */
public class VerifyAnswerRequest {

    private String question;

    @SerializedName("ai_answer")
    private String aiAnswer;

    private Map<String, Object> context;

    public VerifyAnswerRequest() {
    }

    public VerifyAnswerRequest(String question, String aiAnswer, Map<String, Object> context) {
        this.question = question;
        this.aiAnswer = aiAnswer;
        this.context = context;
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
}
