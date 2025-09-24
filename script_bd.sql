CREATE DATABASE IF NOT EXISTS mottu_challenge;

USE mottu_challenge;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Adicione outras tabelas conforme a necessidade da sua aplicação
