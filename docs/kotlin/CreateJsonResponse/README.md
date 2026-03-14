[< Back](../README.md)

# `CreateJsonResponse`

This is a class to create a success JSON response.

```kotlin
import org.springframework.http.ResponseEntity
import io.github.jderstd.spring.response.json.JsonResponse
import io.github.jderstd.spring.response.json.CreateJsonResponse

val response: ResponseEntity<JsonResponse<String>> = CreateJsonResponse
    .dataless()
    .create()
```

And the response will be shown as below:

```json
{
    "success": true,
    "data": null,
    "errors": []
}
```

For adding data, use `CreateJsonResponse.success`:

```kotlin
import org.springframework.http.ResponseEntity
import io.github.jderstd.spring.response.json.JsonResponse
import io.github.jderstd.spring.response.json.CreateJsonResponse

val response: ResponseEntity<JsonResponse<String>> = CreateJsonResponse
    .success<String>()
    .data("Hello, World!")
    .create()
```

And the response will be shown as below:

```json
{
    "success": true,
    "data": "Hello, World!",
    "errors": []
}
```

For creating a failure response, use `CreateJsonResponse.failure`:

```kotlin
import org.springframework.http.ResponseEntity
import io.github.jderstd.spring.response.json.JsonResponse
import io.github.jderstd.spring.response.json.JsonResponseError
import io.github.jderstd.spring.response.json.CreateJsonResponse

val error: JsonResponseError = JsonResponseError()
    .code("bad_request")
    .message("Invalid request.")

val response: ResponseEntity<JsonResponse<String>> = CreateJsonResponse
    .failure()
    .addError(error)
    .create()
```

And the response will be shown as below:

```json
{
    "success": false,
    "data": null,
    "errors": [
        {
            "code": "bad_request",
            "path": [],
            "message": "Invalid request."
        }
    ]
}
```
