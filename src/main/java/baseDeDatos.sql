-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS BaseDeDatos_Ajedrez_TFG;
USE BaseDeDatos_Ajedrez_TFG;

-- Tabla de jugadores
CREATE TABLE Jugadores (
    nombre VARCHAR(100) PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    cuentaIniciada BOOLEAN NOT NULL DEFAULT FALSE,
    password VARCHAR(255) NOT NULL,
    ELO INT NOT NULL DEFAULT 1000
);

-- Tabla de partidas (relación 1:N con Jugadores)
CREATE TABLE Partidas (
    Id_partida INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nombreOponente VARCHAR(100) NOT NULL,
    jugadoCon CHAR(1) CHECK (jugadoCon IN ('B', 'N')), -- 'B' para blancas, 'N' para negras
    partidas TEXT, -- Campo extra para información adicional o la partida completa
    nombre_jugador VARCHAR(100) NOT NULL,
    FOREIGN KEY (nombre_jugador) REFERENCES Jugadores(nombre)
);
