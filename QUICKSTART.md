# Quick Start - AI Verifier Java SDK

## Build and Run in 2 Steps

### Step 1: Start AI Verifier Core Service

In a separate terminal:
```bash
cd /Users/liliia/ai-verifier-core
docker-compose up
```

### Step 2: Build and Run Java Example

```bash
cd /Users/liliia/ai-verifier-java
./run-example.sh
```

## Manual Build

```bash
# Build
mvn clean package

# Run example
cd examples
javac -cp ../target/ai-verifier-java-0.1.0.jar BasicExample.java
java -cp ../target/ai-verifier-java-0.1.0.jar:. BasicExample
```

## Expected Output

```
=== Health Check ===
Status: healthy
Service: ai-verifier-core
Version: 0.1.0

=== Verify Answer ===
Decision ID: 550e8400-e29b-41d4-a716-446655440000
Status: ALLOW
Confidence: 0.85
Reasons: [High confidence AI response]

=== Record Outcome ===
Success: true
Message: Feedback recorded for decision 550e8400-e29b-41d4-a716-446655440000

=== Get Similar Decisions ===
Found 1 similar decisions:
  - Question: What is the capital of France? (Similarity: 0.85)

✓ All examples completed successfully!
```

## Troubleshooting

**Build fails?**
- Make sure Maven is installed: `mvn --version`
- Install Maven: `brew install maven` (macOS) or download from https://maven.apache.org

**Connection refused?**
- Make sure AI Verifier Core is running: `curl http://localhost:8080/health`
- Start the service: `cd /Users/liliia/ai-verifier-core && docker-compose up`

**Java not found?**
- Install Java 11+: `brew install openjdk@11` (macOS)
- Check version: `java -version`

## Use in Your Project

Add to your `pom.xml`:
```xml
<dependency>
    <groupId>io.aiverifier</groupId>
    <artifactId>ai-verifier-java</artifactId>
    <version>0.1.0</version>
</dependency>
```

Then use in your code:
```java
import io.aiverifier.client.*;
import io.aiverifier.client.model.*;

try (AIVerifierClient client = new AIVerifierClient("http://localhost:8080")) {
    VerifyAnswerResponse response = client.verifyAnswer(
        new VerifyAnswerRequest("What is 2+2?", "4", Map.of("domain", "math"))
    );
    System.out.println("Status: " + response.getStatus());
}
```
