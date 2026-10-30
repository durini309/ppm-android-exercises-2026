// "Base de datos" en un archivo JSON. Suficiente para clase, no para producción.
import { existsSync, readFileSync, writeFileSync, copyFileSync } from "node:fs";
import { fileURLToPath } from "node:url";

const SEED_PATH = fileURLToPath(new URL("../data/students.seed.json", import.meta.url));
export const DB_PATH = fileURLToPath(new URL("../data/db.json", import.meta.url));

export function resetDb() {
  copyFileSync(SEED_PATH, DB_PATH);
}

if (!existsSync(DB_PATH)) resetDb();

let students = JSON.parse(readFileSync(DB_PATH, "utf-8"));

function save() {
  writeFileSync(DB_PATH, JSON.stringify(students, null, 2) + "\n");
}

function nextId() {
  return students.reduce((max, s) => Math.max(max, s.id), 0) + 1;
}

export function findAll() {
  return students;
}

export function findById(id) {
  return students.find((s) => s.id === id);
}

export function findByUrl(url) {
  return students.find((s) => s.url === url);
}

export function create({ name, url }) {
  const student = { id: nextId(), name, url };
  students.push(student);
  save();
  return student;
}

export function update(id, fields) {
  const student = findById(id);
  if (!student) return undefined;
  Object.assign(student, fields);
  save();
  return student;
}

export function remove(id) {
  const index = students.findIndex((s) => s.id === id);
  if (index === -1) return false;
  students.splice(index, 1);
  save();
  return true;
}
