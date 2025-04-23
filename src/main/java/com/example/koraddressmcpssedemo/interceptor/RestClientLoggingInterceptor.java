package com.example.koraddressmcpssedemo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
public class RestClientLoggingInterceptor implements ClientHttpRequestInterceptor {
    private static final int MAX_BODY_LENGTH = 1024;
    private static final String REQUEST_LOG_FORMAT = """
        ======= REST Client Request =======
        URI: {}
        Method: {}
        Headers:
        {}
        Body: {}
        """;
    private static final String RESPONSE_LOG_FORMAT = """
        ======= REST Client Response =======
        URI: {}
        Status: {}
        Headers:
        {}
        Body: {}
        """;


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);

        var response = execution.execute(request, body);
        var responseBody = logResponse(request, response);

        return new BufferingClientHttpResponseWrapper(response, responseBody);
    }


    private void logRequest(HttpRequest request, byte[] body) {
        log.debug(REQUEST_LOG_FORMAT,
                  request.getURI(),
                  request.getMethod().name(),
                  formatHeaders(request.getHeaders()),
                  formatBody(request.getHeaders().getContentType(), body));
    }


    private byte[] logResponse(HttpRequest request, ClientHttpResponse response) throws IOException {
        byte[] responseBody = response.getBody().readAllBytes();

        log.debug(RESPONSE_LOG_FORMAT,
                  request.getURI(),
                  response.getStatusCode().value(),
                  formatHeaders(response.getHeaders()),
                  formatBody(response.getHeaders().getContentType(), responseBody));

        // API 응답코드가 정상이 아닌 경우(4xx,5xx) ERROR 레벨 로그를 남긴다.
        if (response.getStatusCode().isError()) {
            log.error(RESPONSE_LOG_FORMAT,
                      request.getURI(),
                      response.getStatusCode().value(),
                      formatHeaders(response.getHeaders()),
                      formatBody(response.getHeaders().getContentType(), responseBody));
        }

        return responseBody;
    }


    private String formatHeaders(HttpHeaders headers) {
        return headers.entrySet().stream()
                      .map(entry -> "    %s: %s".formatted(entry.getKey(), entry.getValue()))
                      .collect(Collectors.joining("\n"));
    }


    private String formatBody(MediaType mediaType, byte[] body) {
        if (mediaType == null || mediaType.equals(MediaType.TEXT_HTML)) {
            return Optional.ofNullable(body)
                           .map(b -> new String(b, StandardCharsets.UTF_8))
                           .orElse(StringUtils.EMPTY);
        }
        if (mediaType.toString().startsWith(APPLICATION_JSON.toString())) {
            return Optional.ofNullable(body)
                           .map(b -> new String(b, StandardCharsets.UTF_8))
                           .map(this::truncateBody)
                           .orElse(StringUtils.EMPTY);
        }

        return body.length > 0 ? String.format("data size : %d bytes", body.length) : StringUtils.EMPTY;
    }


    private String truncateBody(String content) {
        if (content.length() <= MAX_BODY_LENGTH) {
            return content;
        }
        return content.substring(0, MAX_BODY_LENGTH) + "...";
    }
}