# Students API

API REST básica (Node + Express) para practicar Ktor Client desde Android.

## Correr

```bash
cd api
pnpm install
pnpm start          # o: pnpm dev (reinicia al guardar cambios)
```

- Desde la computadora: `http://localhost:3000`
- Desde el **emulador** de Android: `http://10.0.2.2:3000`
- Desde un **teléfono físico** (misma red WiFi): `http://<IP-de-tu-compu>:3000`

Variables opcionales:

```bash
PORT=8080 pnpm start        # cambiar puerto
DELAY_MS=1500 pnpm start    # simular red lenta (para ver estados de loading)
```

Los datos se guardan en `data/db.json`. Para volver a los 37 estudiantes originales:
detén el servidor, corre `pnpm reset` y vuelve a levantarlo.

## Modelo

```json
{
  "id": 1,
  "name": "DIEGO PABLO AYALA FELIPE",
  "url": "https://uvg.instructure.com/courses/46989/users/37626"
}
```

`id` lo asigna el servidor. `url` es única.

## Endpoints

| Método | Ruta              | Body                       | Respuestas                                   |
|--------|-------------------|----------------------------|----------------------------------------------|
| GET    | `/health`         | —                          | `200`                                        |
| GET    | `/students`       | —                          | `200` lista (filtro opcional `?q=diego`)     |
| GET    | `/students/{id}`  | —                          | `200`, `400` id inválido, `404`              |
| POST   | `/students`       | `name`, `url`       | `201` + header `Location`, `400`, `409`      |
| PUT    | `/students/{id}`  | `name`, `url` (ambos) | `200`, `400`, `404`, `409`                 |
| PATCH  | `/students/{id}`  | `name` y/o `url`    | `200`, `400`, `404`, `409`                   |
| DELETE | `/students/{id}`  | —                          | `204` sin body, `404`                        |

Errores siempre con la forma `{ "error": "mensaje" }`.

- `400 Bad Request`: JSON mal formado, campos faltantes o inválidos.
- `404 Not Found`: el estudiante (o la ruta) no existe.
- `409 Conflict`: otro estudiante ya tiene esa `url`.

## Probar con curl

```bash
curl http://localhost:3000/students
curl -X POST http://localhost:3000/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Nuevo","url":"https://uvg.instructure.com/courses/46989/users/1"}'
curl -X PATCH http://localhost:3000/students/1 \
  -H "Content-Type: application/json" -d '{"name":"Otro nombre"}'
curl -i -X DELETE http://localhost:3000/students/1
```

## Nota para Android

La API es `http` (no `https`), así que Android la bloquea por defecto.
Para desarrollo agrega en `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<application
    android:usesCleartextTraffic="true"
    ... >
```
