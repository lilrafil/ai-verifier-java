# AI Verifier Java SDK

Official Java client library for [AI Verifier Core](https://github.com/lilrafil/ai-verifier-core).

[![Maven Central](https://img.shields.io/maven-central/v/io.aiverifier/ai-verifier-java.svg)](https://search.maven.org/artifact/io.aiverifier/ai-verifier-java)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Installation

### Maven

```xml
<dependency>
    <groupId>io.aiverifier</groupId>
    <artifactId>ai-verifier-java</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Gradle

```gradle
implementation 'io.aiverifier:ai-verifier-java:0.1.0'
```

## Requirements

- Java 11 or higher
- AI Verifier Core service running (see [deployment guide](https://github.com/lilrafil/ai-verifier-core#-deployment))

## Quick Start

```java
import io.aiverifier.client.*;
import io.aiverifier.client.model.*;
import java.util.Map;

public class Example {
    public static void main(String[] args) throws AIVerifierException {
        // Create client
        try (AIVerifierClient client = new AIVerifierClient("http://localhost:8080")) {

            // Verify an answer
            VerifyAnswerRequest request = new VerifyAnswerRequest(
                "What is 2+2?",
                "2+2 equals 4",
                Map.of("domain", "math")
            );

            VerifyAnswerResponse response = client.verifyAnswer(request);
            System.out.println("Status: " + response.getStatus());
            System.out.println("Confidence: " + response.getConfidence());

            // Record outcome
            client.recordOutcome(
                response.getDecisionId(),
                OutcomeType.SUCCESS,
                "Correct answer"
            );
        }
    }
}
```

## Usage

### Create Client

```java
// Default timeout (30 seconds)
AIVerifierClient client = new AIVerifierClient("http://localhost:8080");

// Custom timeout
AIVerifierClient client = new AIVerifierClient("http://localhost:8080", 60);

// Use try-with-resources for automatic cleanup
try (AIVerifierClient client = new AIVerifierClient("http://localhost:8080")) {
    // Use client...
}
```

### Verify Answer

```java
import java.util.HashMap;
import java.util.Map;

Map<String, Object> context = new HashMap<>();
context.put("domain", "geography");
context.put("language", "en");

VerifyAnswerRequest request = new VerifyAnswerRequest(
    "What is the capital of France?",
    "The capital of France is Paris.",
    context
);

VerifyAnswerResponse response = client.verifyAnswer(request);

// Check status
switch (response.getStatus()) {
    case ALLOW:
        // High confidence - proceed
        System.out.println("Answer verified with high confidence");
        break;
    case FLAG:
        // Medium confidence - review required
        System.out.println("Answer flagged for review");
        break;
    case ABSTAIN:
        // Low confidence - cannot verify
        System.out.println("Cannot verify answer");
        break;
}

System.out.println("Confidence: " + response.getConfidence());
System.out.println("Reasons: " + response.getReasons());
```

### Record Outcome

```java
// After user interaction or validation
RecordOutcomeResponse outcome = client.recordOutcome(
    decisionId,
    OutcomeType.SUCCESS,  // SUCCESS, FAILURE, or USER_OVERRIDE
    "User confirmed the answer was correct"
);

if (outcome.isSuccess()) {
    System.out.println("Feedback recorded: " + outcome.getMessage());
}
```

### Get Similar Decisions

```java
GetSimilarDecisionsRequest request = new GetSimilarDecisionsRequest(
    "What is machine learning?",
    Map.of("domain", "ai"),
    5  // limit
);

GetSimilarDecisionsResponse response = client.getSimilarDecisions(request);

for (SimilarDecision decision : response.getDecisions()) {
    System.out.println("Question: " + decision.getQuestion());
    System.out.println("Similarity: " + decision.getSimilarityScore());
    System.out.println("Status: " + decision.getVerificationStatus());
}
```

### Health Check

```java
HealthResponse health = client.healthCheck();
System.out.println("Status: " + health.getStatus());
System.out.println("Service: " + health.getService());
System.out.println("Version: " + health.getVersion());
```

## API Reference

### `AIVerifierClient`

Main client class for interacting with AI Verifier Core.

#### Methods

- `HealthResponse healthCheck()` - Check service health
- `VerifyAnswerResponse verifyAnswer(VerifyAnswerRequest)` - Verify an AI answer
- `RecordOutcomeResponse recordOutcome(String decisionId, OutcomeType, String notes)` - Record outcome
- `GetSimilarDecisionsResponse getSimilarDecisions(GetSimilarDecisionsRequest)` - Get similar decisions
- `void close()` - Close client and release resources

### Enums

#### `VerificationStatus`
- `ALLOW` - High confidence (≥0.8)
- `FLAG` - Medium confidence (0.5-0.8)
- `ABSTAIN` - Low confidence (<0.5)

#### `OutcomeType`
- `SUCCESS` - AI answer was correct
- `FAILURE` - AI answer was incorrect
- `USER_OVERRIDE` - User manually overrode verification

## Error Handling

```java
try {
    VerifyAnswerResponse response = client.verifyAnswer(request);
} catch (AIVerifierException e) {
    System.err.println("API call failed: " + e.getMessage());
    // Handle error...
}
```

## Building from Source

```bash
# Clone repository
git clone https://github.com/YOUR_ORG/ai-verifier-java.git
cd ai-verifier-java

# Build
mvn clean install

# Run tests
mvn test

# Run example
cd examples
javac -cp ../target/ai-verifier-java-0.1.0.jar:. BasicExample.java
java -cp ../target/ai-verifier-java-0.1.0.jar:. BasicExample
```

## Examples

See the [examples/](examples/) directory for:
- `BasicExample.java` - Complete usage example
- More examples coming soon...

## Documentation

- **Core Service**: [AI Verifier Core](https://github.com/lilrafil/ai-verifier-core)
- **API Reference**: [SDK Integration Guide](https://github.com/lilrafil/ai-verifier-core/blob/main/SDK_INTEGRATION.md)
- **Javadoc**: Run `mvn javadoc:javadoc` and open `target/site/apidocs/index.html`

## Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

MIT License - see [LICENSE](LICENSE) file for details

## Support

- **Issues**: [GitHub Issues](https://github.com/YOUR_ORG/ai-verifier-java/issues)
- **Core Service**: [AI Verifier Core Issues](https://github.com/lilrafil/ai-verifier-core/issues)

---

**Built with ❤️ for the AI Verifier ecosystem**
