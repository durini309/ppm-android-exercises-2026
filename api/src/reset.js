// Restaura data/db.json con los 37 estudiantes originales.
import { resetDb, DB_PATH } from "./db.js";

resetDb();
console.log(`Base de datos restaurada: ${DB_PATH}`);
