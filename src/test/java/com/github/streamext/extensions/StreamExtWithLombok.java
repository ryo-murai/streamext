package com.github.streamext.extensions;

import lombok.experimental.ExtensionMethod;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.github.streamext.StreamExt.fallback;
import static com.github.streamext.StreamExt.rethrow;
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
                .collect(toList());
    }

    void example1() {
        var paths = List.of("/etc/hosts", "/var/messages", "~/.zshrc").stream()
                .map(Paths::get)
                .collect(toList());
        var a = paths.stream()
                .filter(rethrow((Path path) -> Files.size(path) > 0))
                .collect(toList());
        var b = paths.stream()
                .filter(rethrow(Files::isHidden))
                .collect(toList());
        paths.stream()
                .filter(fallback(Files::isHidden, (Path path, Exception e) -> path.startsWith(".")))
                .collect(toList());
        paths.stream()
                .filter(fallback((Path path) -> Files.size(path) > 0, (Path path, Exception e) -> path.toFile().length() > 0))
                .filter(fallback((Path path) -> Files.size(path) > 0, (Path path, Exception e) -> true))
                .collect(toList());
    }
}
