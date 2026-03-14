[< Back](../README.md)

# `CreateJsonResponse`

This is a class to create a success JSON response.

```java
import org.springframework.http.ResponseEntity;
import io.github.jderstd.spring.response.json.JsonResponse;
import io.github.jderstd.spring.response.json.CreateJsonResponse;

ResponseEntity<JsonResponse<Void>> response = CreateJsonResponse
    .dataless()
    .create();
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

```java
import org.springframework.http.ResponseEntity;
import io.github.jderstd.spring.response.json.JsonResponse;
import io.github.jderstd.spring.response.json.CreateJsonResponse;

ResponseEntity<JsonResponse<String>> response = CreateJsonResponse
    .<String>success()
    .setData("Hello, World!")
    .create();
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

```java
import org.springframework.http.ResponseEntity;
import io.github.jderstd.spring.response.json.JsonResponse;
import io.github.jderstd.spring.response.json.JsonResponseError;
import io.github.jderstd.spring.response.json.CreateJsonResponse;

JsonResponseError error = new JsonResponseError()
    .setCode("bad_request")
    .setMessage("Invalid request.");

ResponseEntity<JsonResponse<String>> response = CreateJsonResponse
    .failure()
    .addError(error)
    .create();
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
