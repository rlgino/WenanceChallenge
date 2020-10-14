## Wenance Calc
Esta es una app para ingreso de Wenance

### Request ejemplo:

```
curl --location --request GET 'localhost:8080/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "date": "2020-10-14 18:02:34.563",
    "pattern": "yyyy-MM-dd HH:mm:ss.SSS"
}'
```