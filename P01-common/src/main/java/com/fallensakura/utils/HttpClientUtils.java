package com.fallensakura.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.StringJoiner;

public class HttpClientUtils {
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    private HttpClientUtils() {}

    public static String doGet(String url, Map<String, String> params) {
        String fullUrl = buildUrl(url, params);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .timeout(Duration.ofSeconds(8))
                .GET()
                .build();

        try {
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new RuntimeException("GET failed, status= " + response.statusCode() + ", url=" + fullUrl);
            }

            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static String buildUrl(String url, Map<String, String> params) {
        if (params == null || params.isEmpty()) return url;

        StringJoiner sj = new StringJoiner("&");

        for (Map.Entry<String, String > e : params.entrySet()) {
            String key = URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8);
            String value = URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8);

            sj.add(key + "=" + value);
        }

        return url + (url.contains("?") ? "&" : "?") + sj;
    }
}
