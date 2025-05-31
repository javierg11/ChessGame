-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS TFGChessGame;
USE TFGChessGame;

-- Tabla de jugadores
CREATE TABLE Jugadores (
    nombre VARCHAR(50) PRIMARY KEY,
    email VARCHAR(50),
    cuentaIniciada BOOLEAN NOT NULL DEFAULT TRUE,
    password VARCHAR(255),
    ELO INT NOT NULL DEFAULT 1000
);

-- Tabla de partidas (relación 1:N con Jugadores)
CREATE TABLE Partidas (
    Id_partida INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    nombrePropio VARCHAR(50) NOT NULL,
    nombreOponente VARCHAR(50) NOT NULL,
    jugadoCon CHAR(1) CHECK (jugadoCon IN ('B', 'N')), -- 'B' para blancas, 'N' para negras
    partidas TEXT, -- Campo extra para información adicional o la partida completa
    FOREIGN KEY (nombrePropio) REFERENCES Jugadores(nombre)
);
