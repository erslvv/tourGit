package kz.safetrip.safetrip.service.telegram;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.safetrip.safetrip.model.dto.telegram.TelegramSendMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBotClient {

    private final ObjectMapper objectMapper;

    @Value("${app.telegram.bot-service-url}")
    private String botServiceUrl;

    @Value("${app.telegram.bot-secret}")
    private String botSecret;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public void sendMessage(String chatId, String text) {
        try {
            TelegramSendMessageRequest requestBody = new TelegramSendMessageRequest();
            requestBody.setChatId(chatId);
            requestBody.setText(text);

            String json = objectMapper.writeValueAsString(requestBody);

            String url = botServiceUrl.endsWith("/")
                    ? botServiceUrl + "api/messages"
                    : botServiceUrl + "/api/messages";

            log.info("Sending Telegram message request. url={}, chatId={}, body={}", url, chatId, json);

            HttpRequest request = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(15))
                    .header("Content-Type", "application/json; charset=utf-8")
                    .header("Accept", "application/json")
                    .header("X-Bot-Secret", botSecret)
                    .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
            );

            log.info(
                    "Telegram bot response. status={}, body={}",
                    response.statusCode(),
                    response.body()
            );

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IllegalStateException(
                        "Telegram bot returned error. status=" + response.statusCode()
                                + ", body=" + response.body()
                );
            }

            log.info("Telegram message sent through bot. chatId={}", chatId);
        } catch (Exception e) {
            log.error("Failed to send Telegram message to chatId={}", chatId, e);
            throw new IllegalStateException("Failed to send Telegram message", e);
        }
    }
}