package io.aiverifier.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import io.aiverifier.client.model.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Java client for AI Verifier Core service.
 * <p>
 * This is a lightweight HTTP client that communicates with the AI Verifier Core REST API.
 * </p>
 *
 * <h2>Example Usage:</h2>
 * <pre>{@code
 * AIVerifierClient client = new AIVerifierClient("http://localhost:8080");
 *
 * // Verify an answer
 * VerifyAnswerRequest request = new VerifyAnswerRequest(
 *     "What is 2+2?",
 *     "2+2 equals 4",
 *     Map.of("domain", "math")
 * );
 *
 * VerifyAnswerResponse response = client.verifyAnswer(request);
 * System.out.println("Status: " + response.getStatus());
 * System.out.println("Confidence: " + response.getConfidence());
 *
 * // Record outcome
 * client.recordOutcome(response.getDecisionId(), OutcomeType.SUCCESS, "Correct answer");
 * }</pre>
 *
 * @version 0.1.0
 */
public class AIVerifierClient implements AutoCloseable {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final String baseUrl;
    private final OkHttpClient httpClient;
    private final Gson gson;

    /**
     * Creates a new AI Verifier client with default timeout (30 seconds).
     *
     * @param baseUrl Base URL of the AI Verifier service (e.g., "http://localhost:8080")
     */
    public AIVerifierClient(String baseUrl) {
        this(baseUrl, 30);
    }

    /**
     * Creates a new AI Verifier client with custom timeout.
     *
     * @param baseUrl Base URL of the AI Verifier service
     * @param timeoutSeconds Timeout in seconds for HTTP requests
     */
    public AIVerifierClient(String baseUrl, int timeoutSeconds) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .writeTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .build();
        this.gson = new GsonBuilder().create();
    }

    /**
     * Checks the health of the AI Verifier service.
     *
     * @return Health status information
     * @throws AIVerifierException if the request fails
     */
    public HealthResponse healthCheck() throws AIVerifierException {
        Request request = new Request.Builder()
                .url(baseUrl + "/health")
                .get()
                .build();

        return execute(request, HealthResponse.class);
    }

    /**
     * Verifies an AI-generated answer.
     *
     * @param request The verification request containing question, answer, and context
     * @return Verification response with decision ID, status, confidence, and reasons
     * @throws AIVerifierException if the request fails
     */
    public VerifyAnswerResponse verifyAnswer(VerifyAnswerRequest request) throws AIVerifierException {
        String json = gson.toJson(request);
        RequestBody body = RequestBody.create(json, JSON);

        Request httpRequest = new Request.Builder()
                .url(baseUrl + "/api/verify")
                .post(body)
                .build();

        return execute(httpRequest, VerifyAnswerResponse.class);
    }

    /**
     * Records the outcome of a verification decision (closes the feedback loop).
     *
     * @param decisionId UUID of the decision from verifyAnswer
     * @param outcome The outcome type (SUCCESS, FAILURE, USER_OVERRIDE)
     * @param notes Optional notes about the outcome
     * @return Response indicating success or failure
     * @throws AIVerifierException if the request fails
     */
    public RecordOutcomeResponse recordOutcome(String decisionId, OutcomeType outcome, String notes)
            throws AIVerifierException {
        RecordOutcomeRequest request = new RecordOutcomeRequest(decisionId, outcome, notes);
        String json = gson.toJson(request);
        RequestBody body = RequestBody.create(json, JSON);

        Request httpRequest = new Request.Builder()
                .url(baseUrl + "/api/outcome")
                .post(body)
                .build();

        return execute(httpRequest, RecordOutcomeResponse.class);
    }

    /**
     * Gets similar past decisions for debugging and analysis.
     *
     * @param request Request containing question, context, and limit
     * @return List of similar decisions with similarity scores
     * @throws AIVerifierException if the request fails
     */
    public GetSimilarDecisionsResponse getSimilarDecisions(GetSimilarDecisionsRequest request)
            throws AIVerifierException {
        String json = gson.toJson(request);
        RequestBody body = RequestBody.create(json, JSON);

        Request httpRequest = new Request.Builder()
                .url(baseUrl + "/api/similar")
                .post(body)
                .build();

        return execute(httpRequest, GetSimilarDecisionsResponse.class);
    }

    private <T> T execute(Request request, Class<T> responseClass) throws AIVerifierException {
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "No error details";
                throw new AIVerifierException(
                    "HTTP " + response.code() + ": " + response.message() + " - " + errorBody
                );
            }

            String responseBody = response.body().string();
            return gson.fromJson(responseBody, responseClass);
        } catch (IOException e) {
            throw new AIVerifierException("Failed to execute request: " + e.getMessage(), e);
        }
    }

    /**
     * Closes the HTTP client and releases resources.
     */
    @Override
    public void close() {
        httpClient.dispatcher().executorService().shutdown();
        httpClient.connectionPool().evictAll();
    }
}
