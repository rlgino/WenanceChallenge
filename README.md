## Wenance Calc
Esta es una app para ingreso de Wenance

### Ejecución de la app
La aplicación se puede levantar de forma local mediante 2 formas:

* Mediante la ejecución de la función main en el archivo
```
WenanceChallengeApplication.java
  ```

* Mediante la linea de comandos con el plugin de springboot para gradle:

```
./gradlew bootRun
```

### Request ejemplo:
#### Buscar precio en una fecha
En request ejemplo para buscar precio en una fecha corresponde 
al endpoint ``/find-one`` y solo se debe envíar un body con los campos
date que significa la fecha y el pattern para interpretar esa fecha:
```
curl --location --request GET 'localhost:8080/find-one' \
--header 'Content-Type: application/json' \
--data-raw '{
    "date": "2020-10-14 18:02:34.563",
    "pattern": "yyyy-MM-dd HH:mm:ss.SSS"
}'
```

#### Buscar 