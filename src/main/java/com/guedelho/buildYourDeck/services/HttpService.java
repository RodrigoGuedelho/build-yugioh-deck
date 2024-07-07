package com.guedelho.buildYourDeck.services;

import java.io.IOException;
import java.net.http.HttpResponse;

public interface HttpService {
    public HttpResponse<byte[]> getBytes(String uri, String... headers) throws IOException, InterruptedException;

    public HttpResponse<String> get(String uri, String... headers) throws IOException, InterruptedException;

    public HttpResponse<String> post(String uri, String body, String... headers) throws IOException, InterruptedException;

    public HttpResponse<String> put(String uri, String body, String... headers) throws IOException, InterruptedException;
}
