package com.guedelho.buildYourDeck.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpService {
    private String url;
    private int TIMEOUT = 140;

    public HttpService(String url) {
        this.url = url;
    }

    public HttpResponse<byte[]> getBytes(String uri, String ... headers) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url + uri))
                .headers(headers)
                .timeout(Duration.ofSeconds(TIMEOUT))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT))
                .build();

        HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
        return response;
    }

    public HttpResponse<String> get(String uri, String ... headers) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url + uri))
                .headers(headers)
                .timeout(Duration.ofSeconds(TIMEOUT))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> post(String uri, String body, String ... headers) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .uri(URI.create(url + uri))
                .headers(headers)
                .timeout(Duration.ofSeconds(TIMEOUT))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> put(String uri,  String body, String ... headers) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(body != null ? HttpRequest.BodyPublishers.ofString(body) : HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(url + uri))
                .headers(headers)
                .timeout(Duration.ofSeconds(TIMEOUT))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

}