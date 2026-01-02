import io.aiverifier.client.*;
import io.aiverifier.client.model.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic example of using the AI Verifier Java SDK.
 */
public class BasicExample {

    public static void main(String[] args) {
        // Create client
        try (AIVerifierClient client = new AIVerifierClient("http://localhost:8080")) {

            // 1. Health Check
            System.out.println("=== Health Check ===");
            HealthResponse health = client.healthCheck();
            System.out.println("Status: " + health.getStatus());
            System.out.println("Service: " + health.getService());
            System.out.println("Version: " + health.getVersion());
            System.out.println();

            // 2. Verify Answer
            System.out.println("=== Verify Answer ===");
            Map<String, Object> context = new HashMap<>();
            context.put("domain", "geography");
            context.put("language", "en");

            VerifyAnswerRequest verifyRequest = new VerifyAnswerRequest(
                "What is the capital of France?",
                "The capital of France is Paris.",
                context
            );

            VerifyAnswerResponse verifyResponse = client.verifyAnswer(verifyRequest);
            System.out.println("Decision ID: " + verifyResponse.getDecisionId());
            System.out.println("Status: " + verifyResponse.getStatus());
            System.out.println("Confidence: " + verifyResponse.getConfidence());
            System.out.println("Reasons: " + verifyResponse.getReasons());
            System.out.println();

            // 3. Record Outcome
            System.out.println("=== Record Outcome ===");
            RecordOutcomeResponse outcomeResponse = client.recordOutcome(
                verifyResponse.getDecisionId(),
                OutcomeType.SUCCESS,
                "User confirmed the answer was accurate"
            );
            System.out.println("Success: " + outcomeResponse.isSuccess());
            System.out.println("Message: " + outcomeResponse.getMessage());
            System.out.println();

            // 4. Get Similar Decisions
            System.out.println("=== Get Similar Decisions ===");
            Map<String, Object> searchContext = new HashMap<>();
            searchContext.put("domain", "geography");

            GetSimilarDecisionsRequest similarRequest = new GetSimilarDecisionsRequest(
                "What is the capital of France?",
                searchContext,
                5
            );

            GetSimilarDecisionsResponse similarResponse = client.getSimilarDecisions(similarRequest);
            System.out.println("Found " + similarResponse.getDecisions().size() + " similar decisions:");
            for (SimilarDecision decision : similarResponse.getDecisions()) {
                System.out.printf("  - Question: %s (Similarity: %.2f)%n",
                    decision.getQuestion(), decision.getSimilarityScore());
            }

            System.out.println("\n✓ All examples completed successfully!");

        } catch (AIVerifierException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
