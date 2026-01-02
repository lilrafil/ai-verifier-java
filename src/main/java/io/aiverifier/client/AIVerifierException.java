package io.aiverifier.client;

/**
 * Exception thrown when AI Verifier API calls fail.
 */
public class AIVerifierException extends Exception {

    public AIVerifierException(String message) {
        super(message);
    }

    public AIVerifierException(String message, Throwable cause) {
        super(message, cause);
    }
}
