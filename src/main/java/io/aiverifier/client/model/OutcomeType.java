package io.aiverifier.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Type of outcome for a verification decision.
 */
public enum OutcomeType {
    /**
     * The AI answer was correct/successful
     */
    @SerializedName("Success")
    SUCCESS,

    /**
     * The AI answer was incorrect/failed
     */
    @SerializedName("Failure")
    FAILURE,

    /**
     * User manually overrode the verification
     */
    @SerializedName("UserOverride")
    USER_OVERRIDE
}
