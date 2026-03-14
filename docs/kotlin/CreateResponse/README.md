[< Back](../README.md)

# `CreateResponse`

This is a class to create pure response.

```kotlin
import org.springframework.http.ResponseEntity
import io.github.jderstd.spring.response.CreateResponse

val response: ResponseEntity<String> = CreateResponse<String>()
    .body("Hello, World!")
    .create()
```

And the response will be shown as below:

```
Hello, World!
```