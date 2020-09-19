# JWTDemo
Sprint Boot JWT Demo

### authenticate
```
curl --location --request POST 'http://localhost:8080/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '{
	"username": "foo",
	"password": "foo"
}'
```

### hello
```
curl --location --request GET 'http://localhost:8080/hello' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <jwt>' \
```