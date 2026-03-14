[< Back](../README.md)

# `NotFoundHandler`

This is a handler to handle not found exception.

```kotlin
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.NoHandlerFoundException

import io.github.jderstd.spring.response.json.JsonResponse
import io.github.jderstd.spring.handler.NotFoundHandler

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoHandlerFoundException::class)
    public fun notFoundHandler(ex: NoHandlerFoundException): ResponseEntity<JsonResponse<Void>> {
        return NotFoundHandler.handle(ex)
    }
}
```
