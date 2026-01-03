package io.aiverifier.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Verification status returned by the AI Verifier.
 */
public enum VerificationStatus {
    /**
     * High confidence (&gt;=0.8) - Answer passes verification
     */
    @SerializedName("Allow")
    ALLOW,

    /**
     * Medium confidence (0.5-0.8) - Requires human review
     */
    @SerializedName("Flag")
    FLAG,

    /**
     * Low confidence (&lt;0.5) - Cannot verify
     */
    @SerializedName("Abstain")
    ABSTAIN
}
