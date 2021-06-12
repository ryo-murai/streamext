# Stream API Extension

This Library enables you handling Checked Exception in the Java Stream API.
See `mapE()` in below example.
```java
@ExtensionMethod({StreamExtOps.class})
class RestService {
    List<HttpResponse> sendRequests(List<HttpRequest> requests) {
        return requests.stream()
                .map(req -> executor.submit(() -> httpClient.send(req, ofString())))
                .mapE(Future::get)
                .collect(toList());
    }
}
```

[`Future::get`](https://docs.oracle.com/javase/jp/8/docs/api/java/util/concurrent/Future.html#get--) throws the checked exception, `ExecutionException` and `InterruptedException`. 
You can't pass `Future::get` directly in `Stream.map()`.
Instead, you can use `mapE()` of the Java Standard Stream API extended by this library. 
When the `future.get()` throws a checked exception, this library re-throw it with wrapped by `FunctionExecutionException`.

## Extension APIs
### mapE(func)
when `func` throws an exception, re-throw it with wrapped by `FunctionExecutionException`.
```java
stream()
  .mapE(Future::get)
  .collect(toList());
```

### mapE(func, fallback)
when `func` throws an exception, `fallback` function will be called to handle the exception.  
`fallback` should have two params: the first is the source element of the stream,
the second is the exception thrown by the `func`.

```java
stream()
  .mapE(Future::get, (future, exception) -> exception.getMessage())
  .collect(toList());
```

### mapQuiet(func)
when `func` throws an exception, it maps to `null`;
```java
stream()
  .mapQuiet(Future::get)   // if fails, map to null
  .collect(toList());
```

### Other extension APIs
* `flatMapE(func)` / `flatMapE(func, fallback)` / `flatMapQuiet(func)`
* `filterE(predicate)` / `filterE(predicate, fallback)` / `filterQuiet(predicate)`
* `forEachE(consumer)` / `forEachE(predicate, fallback)` / `forEachQuiet(predicate)`
* `anyMatchE` / ...
* `allMatchE` / ...
* `nonMatchE` / ...
* `forEachOrderedE` / ...

### How works "Quietly" ?
When an exception occurred, `Quiet` API handles it like the followings.
* function in `mapQuiet()`: returns `null`
* function in `flatMapQuiet()`: returns an empty `Stream`
* `predicate` for `filterQuiet()`, `anyMatchQuiet()` etc: returns `false`
* `consumer` for `forEachQuiet()`, `forEachOrderedQuiet()`: do nothing

## How it extends ?
This library uses lombok's [`@ExtensionMethod`](https://projectlombok.org/features/experimental/ExtensionMethod).
```java
@ExtensionMethod({StreamExtOps.class})
```
By this annotation, the Java Standard Stream API will have the extension methods.

----
## Don't want to use Lombok ?
or already using lombok, but don't want to use experimental features ?

This library also provides some utilities can be used without the lombok.
See `rethrow()` in below example.

```java
    requests.stream()
        .map(req -> executor.submit(() -> httpClient.send(req, ofString())))
        .map(rethrow(Future::get))
        .collect(toList());
```


## Utility APIs
### rethrow(func)
when `func` throws an exception, re-throw it with wrapped by `FunctionExecutionException`.
```java
stream()
  .map(rethrow(Future::get))
  .collect(toList());
```

### fallback(func, fallbackFunction)
when `func` throws an exception, `fallbackFunction` function will be called to handle the exception.  
`fallbackFunction` should have two params: the first is the source element of the stream,
the second is the exception thrown by the `func`.

```java
stream()
  .map(fallback(Future::get, (future, exception) -> exception.getMessage()))
  .collect(toList());
```

### quiet(func)
when `func` throws an exception, it maps to `null`;
```java
stream()
  .map(quiet(Future::get))   // if fails, map to null
  .collect(toList());
```

### Other Utilities
`rethrow` / `fallback` / `quiet` can be used also for
`Predicate` and `Consumer`.