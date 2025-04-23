package com.example.koraddressmcpssedemo.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferingClientHttpResponseWrapper implements ClientHttpResponse {
    private final ClientHttpResponse response;
    private byte[] body;


    BufferingClientHttpResponseWrapper(ClientHttpResponse response, byte[] body) {
        this.response = response;
        this.body = body;
    }


    public HttpStatusCode getStatusCode() throws IOException {
        return this.response.getStatusCode();
    }


    public String getStatusText() throws IOException {
        return this.response.getStatusText();
    }


    public HttpHeaders getHeaders() {
        return this.response.getHeaders();
    }


    public InputStream getBody() throws IOException {
        if (this.body == null) {
            this.body = StreamUtils.copyToByteArray(this.response.getBody());
        }

        return new ByteArrayInputStream(this.body);
    }


    public void close() {
        this.response.close();
    }
}
