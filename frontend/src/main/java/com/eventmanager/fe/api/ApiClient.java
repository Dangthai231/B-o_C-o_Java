package com.eventmanager.fe.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Gom toàn bộ việc gọi REST API của backend vào một chỗ:
 * màn hình chỉ cần gọi ApiClient.get("/api/...") và nhận về JSON.
 * Lỗi (không kết nối được, HTTP 4xx/5xx) được ném ra dưới dạng ApiException
 * với câu thông báo lấy từ trường "message" mà backend trả về.
 */
public class ApiClient {

    public static final String BASE_URL = "http://localhost:8080";

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public static JsonElement get(String path) throws ApiException {
        return send(builder(path).GET().build());
    }

    public static JsonElement post(String path, JsonObject body) throws ApiException {
        return send(builder(path).POST(bodyOf(body)).build());
    }

    public static JsonElement put(String path, JsonObject body) throws ApiException {
        return send(builder(path).PUT(bodyOf(body)).build());
    }

    // Mã hóa tham số trên URL (tiếng Việt, khoảng trắng...)
    public static String encode(String value) {
        return URLEncoder.encode(value == null ? "" : value, StandardCharsets.UTF_8);
    }

    private static HttpRequest.Builder builder(String path) {
        return HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json; charset=UTF-8");
    }

    private static HttpRequest.BodyPublisher bodyOf(JsonObject body) {
        return body == null
                ? HttpRequest.BodyPublishers.noBody()
                : HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8);
    }

    private static JsonElement send(HttpRequest request) throws ApiException {
        HttpResponse<String> response;
        try {
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new ApiException("Không kết nối được máy chủ. Hãy chạy backend (cổng 8080) trước.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException("Yêu cầu bị gián đoạn");
        }

        JsonElement json;
        try {
            String body = response.body();
            json = (body == null || body.isBlank()) ? JsonNull.INSTANCE : JsonParser.parseString(body);
        } catch (RuntimeException e) {
            json = JsonNull.INSTANCE;
        }

        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return json;
        }

        String message = "Lỗi máy chủ (HTTP " + response.statusCode() + ")";
        if (json.isJsonObject() && json.getAsJsonObject().has("message")
                && !json.getAsJsonObject().get("message").isJsonNull()) {
            message = json.getAsJsonObject().get("message").getAsString();
        }
        throw new ApiException(message);
    }

    public static class ApiException extends Exception {
        public ApiException(String message) {
            super(message);
        }
    }
}
