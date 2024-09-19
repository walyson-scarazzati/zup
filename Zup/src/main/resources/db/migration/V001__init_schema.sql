-- V1__init_schema.sql

CREATE DATABASE IF NOT EXISTS zup;

USE zup;

-- Create 'pessoas' table
CREATE TABLE IF NOT EXISTS pessoas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    data_nascimento DATE NOT NULL
);

-- Insert some initial data
INSERT INTO pessoas (nome, email, cpf, data_nascimento) VALUES
('João Silva', 'joao.silva@example.com', '12345678901', '1990-05-15'),
('Maria Souza', 'maria.souza@example.com', '98765432100', '1985-08-23');
