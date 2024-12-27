package com.github.streamext.extensions;

import lombok.experimental.ExtensionMethod;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toList;

@ExtensionMethod({StreamExtOps.class})
public class StreamExtWithLombok {
    void runStream() {
        var httpClient = HttpClient.newHttpClient();
        var hosts = List.of("example.com", "example.org");
        var urls = hosts.stream()
                .map(s -> String.format("http://%s/info", s))
                .map(URI::create)
                .list();

        var executor = Executors.newFixedThreadPool(hosts.size());
        var list = urls.stream()
                .map(uri -> HttpRequest.newBuilder(uri).build())
                .map(req -> executor.submit(() -> httpClient.send(req, HttpResponse.BodyHandlers.ofString())))
                .mapE(Future::get)
                .map(HttpResponse::body)
                .list();
    }
}
