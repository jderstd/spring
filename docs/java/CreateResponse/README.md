[< Back](../README.md)

# `CreateResponse`

This is a class to create pure response.

```java
import org.springframework.http.ResponseEntity;
import io.github.jderstd.spring.response.CreateResponse;

ResponseEntity<String> response = new CreateResponse<String>()
    .setBody("Hello, World!")
    .create();
```

And the response will be shown as below:

```
Hello, World!
```
