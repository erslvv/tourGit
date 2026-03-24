package kz.safetrip.safetrip.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.safetrip.safetrip.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {
    private static final Base64.Encoder URL_ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder URL_DECODER = Base64.getUrlDecoder();
    private final JwtProperties properties;
    private final ObjectMapper objectMapper;

    public String generateToken(User user) {
        try {
            long issuedAt = Instant.now().getEpochSecond();
            long expiresAt = Instant.now().plusSeconds(properties.expirationMinutes() * 60).getEpochSecond();
            Map<String, Object> header = Map.of("alg", "HS256", "typ", "JWT");
            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("sub", user.getEmail());
            payload.put("userId", user.getId());
            payload.put("role", user.getRole().name());
            payload.put("iat", issuedAt);
            payload.put("exp", expiresAt);
            String headerPart = encodeJson(header);
            String payloadPart = encodeJson(payload);
            String content = headerPart + "." + payloadPart;
            return content + "." + sign(content);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to generate JWT token", e);
        }
    }

    public String extractUsername(String token) { return (String) parsePayload(token).get("sub"); }

    public boolean isTokenValid(String token, User user) {
        Map<String, Object> payload = parsePayload(token);
        Object expValue = payload.get("exp");
        long exp = expValue instanceof Number n ? n.longValue() : Long.parseLong(String.valueOf(expValue));
        String username = (String) payload.get("sub");
        return username != null && username.equalsIgnoreCase(user.getEmail()) && exp > Instant.now().getEpochSecond() && isSignatureValid(token);
    }

    private boolean isSignatureValid(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return false;
            String expected = sign(parts[0] + "." + parts[1]);
            return expected.equals(parts[2]);
        } catch (Exception e) {
            return false;
        }
    }

    private Map<String, Object> parsePayload(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3 || !isSignatureValid(token)) throw new IllegalArgumentException("Invalid JWT token");
            return objectMapper.readValue(URL_DECODER.decode(parts[1]), new TypeReference<>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }

    private String encodeJson(Map<String, Object> map) throws Exception { return URL_ENCODER.encodeToString(objectMapper.writeValueAsBytes(map)); }

    private String sign(String content) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(properties.secret().getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return URL_ENCODER.encodeToString(mac.doFinal(content.getBytes(StandardCharsets.UTF_8)));
    }
}
