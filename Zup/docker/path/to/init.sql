-- Criação do banco de dados 'zup' se não existir
CREATE DATABASE IF NOT EXISTS zup;

-- Usar o banco de dados
USE zup;

-- Alteração de senha para o usuário 'root'
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '123456';
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123456';

-- Concedendo todas as permissões ao usuário
GRANT ALL PRIVILEGES ON zup.* TO 'root'@'localhost';
GRANT ALL PRIVILEGES ON zup.* TO 'root'@'%';

-- Aplicando as mudanças
FLUSH PRIVILEGES;


-- Criação da tabela 'pessoas'
CREATE TABLE IF NOT EXISTS pessoas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    data_nascimento DATE NOT NULL
);

-- Inserção de dados iniciais na tabela 'pessoas'
INSERT INTO pessoas (nome, email, cpf, data_nascimento) VALUES
('João Silva', 'joao.silva@example.com', '12345678901', '1990-05-15'),
('Maria Souza', 'maria.souza@example.com', '98765432100', '1985-08-23');
