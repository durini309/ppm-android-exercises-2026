import express from "express";
import * as db from "./db.js";

const app = express();
const PORT = Number(process.env.PORT) || 3000;
// Retraso artificial opcional (ms) para poder ver estados de "loading" en la app.
const DELAY_MS = Number(process.env.DELAY_MS) || 0;

app.use(express.json());

app.use((req, res, next) => {
  const start = Date.now();
  res.on("finish", () => {
    console.log(`${req.method} ${req.originalUrl} -> ${res.statusCode} (${Date.now() - start}ms)`);
  });
  next();
});

if (DELAY_MS > 0) {
  app.use((req, res, next) => setTimeout(next, DELAY_MS));
}

// ---------- Helpers ----------

function parseId(req, res) {
  const id = Number(req.params.id);
  if (!Number.isInteger(id) || id <= 0) {
    res.status(400).json({ error: "El id debe ser un entero positivo" });
    return null;
  }
  return id;
}

function isValidUrl(value) {
  try {
    const url = new URL(value);
    return url.protocol === "http:" || url.protocol === "https:";
  } catch {
    return false;
  }
}

// Valida el body. Si `partial` es true (PATCH) los campos son opcionales,
// pero al menos uno debe venir. Devuelve { fields } o { error }.
function validateStudent(body, { partial = false } = {}) {
  if (typeof body !== "object" || body === null || Array.isArray(body)) {
    return { error: "El body debe ser un objeto JSON" };
  }

  const fields = {};

  if (body.name !== undefined) {
    if (typeof body.name !== "string" || body.name.trim() === "") {
      return { error: "'name' debe ser un texto no vacío" };
    }
    fields.name = body.name.trim();
  } else if (!partial) {
    return { error: "'name' es requerido" };
  }

  if (body.url !== undefined) {
    if (typeof body.url !== "string" || !isValidUrl(body.url)) {
      return { error: "'url' debe ser una URL válida (http/https)" };
    }
    fields.url = body.url.trim();
  } else if (!partial) {
    return { error: "'url' es requerido" };
  }

  if (partial && Object.keys(fields).length === 0) {
    return { error: "Envía al menos uno de: 'name', 'url'" };
  }

  return { fields };
}

// url es única: 409 Conflict si ya la tiene otro estudiante.
function urlTaken(url, exceptId) {
  const existing = url && db.findByUrl(url);
  return existing && existing.id !== exceptId;
}

// ---------- Rutas ----------

app.get("/health", (req, res) => {
  res.status(200).json({
    status: "ok",
    uptime_seconds: Math.round(process.uptime()),
    timestamp: new Date().toISOString(),
  });
});

// GET /students?q=texto  -> filtra por nombre (opcional)
app.get("/students", (req, res) => {
  const q = typeof req.query.q === "string" ? req.query.q.trim().toLowerCase() : "";
  const students = db.findAll();
  const result = q ? students.filter((s) => s.name.toLowerCase().includes(q)) : students;
  res.status(200).json(result);
});

app.get("/students/:id", (req, res) => {
  const id = parseId(req, res);
  if (id === null) return;

  const student = db.findById(id);
  if (!student) return res.status(404).json({ error: `Estudiante ${id} no encontrado` });

  res.status(200).json(student);
});

app.post("/students", (req, res) => {
  const { fields, error } = validateStudent(req.body);
  if (error) return res.status(400).json({ error });

  if (urlTaken(fields.url)) {
    return res.status(409).json({ error: "Ya existe un estudiante con esa 'url'" });
  }

  const student = db.create(fields);
  res.status(201).location(`/students/${student.id}`).json(student);
});

// PUT reemplaza el recurso completo: requiere todos los campos.
app.put("/students/:id", (req, res) => {
  const id = parseId(req, res);
  if (id === null) return;

  const { fields, error } = validateStudent(req.body);
  if (error) return res.status(400).json({ error });

  if (!db.findById(id)) return res.status(404).json({ error: `Estudiante ${id} no encontrado` });

  if (urlTaken(fields.url, id)) {
    return res.status(409).json({ error: "Ya existe un estudiante con esa 'url'" });
  }

  res.status(200).json(db.update(id, fields));
});

// PATCH actualiza solo los campos enviados.
app.patch("/students/:id", (req, res) => {
  const id = parseId(req, res);
  if (id === null) return;

  const { fields, error } = validateStudent(req.body, { partial: true });
  if (error) return res.status(400).json({ error });

  if (!db.findById(id)) return res.status(404).json({ error: `Estudiante ${id} no encontrado` });

  if (urlTaken(fields.url, id)) {
    return res.status(409).json({ error: "Ya existe un estudiante con esa 'url'" });
  }

  res.status(200).json(db.update(id, fields));
});

app.delete("/students/:id", (req, res) => {
  const id = parseId(req, res);
  if (id === null) return;

  if (!db.remove(id)) return res.status(404).json({ error: `Estudiante ${id} no encontrado` });

  res.status(204).end();
});

// ---------- Errores ----------

// Ruta inexistente
app.use((req, res) => {
  res.status(404).json({ error: `Ruta no encontrada: ${req.method} ${req.originalUrl}` });
});

// JSON mal formado (lo lanza express.json()) u otros errores
app.use((err, req, res, next) => {
  if (err.type === "entity.parse.failed") {
    return res.status(400).json({ error: "JSON mal formado" });
  }
  console.error(err);
  res.status(err.status || 500).json({ error: "Error interno del servidor" });
});

// 0.0.0.0 para que también se pueda acceder desde un teléfono físico en la misma red.
app.listen(PORT, "0.0.0.0", () => {
  console.log(`Students API en http://localhost:${PORT}`);
  console.log(`Desde el emulador de Android: http://10.0.2.2:${PORT}`);
});
