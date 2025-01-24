CREATE DATABASE GerenciadorDeVeiculos;
USE GerenciadorDeVeiculos;

-- Tabela principal: Veiculo
CREATE TABLE Veiculo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(100) NOT NULL,
    fabricante VARCHAR(100) NOT NULL,
    ano INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL
);

-- Tabela para Carro
CREATE TABLE Carro (
    id INT PRIMARY KEY,
    quantidade_portas INT NOT NULL,
    tipo_combustivel ENUM('gasolina', 'etanol', 'diesel', 'flex') NOT NULL,
    FOREIGN KEY (id) REFERENCES Veiculo(id) ON DELETE CASCADE
);

-- Tabela para Moto
CREATE TABLE Moto (
    id INT PRIMARY KEY,
    cilindrada INT NOT NULL,
    FOREIGN KEY (id) REFERENCES Veiculo(id) ON DELETE CASCADE
);


-- Inserindo dados na tabela Veiculo
INSERT INTO Veiculo (modelo, fabricante, ano, preco) VALUES
('Civic', 'Honda', 2020, 95000.00), -- ID será 1
('Gol', 'Volkswagen', 2019, 45000.00), -- ID será 2
('CB 500F', 'Honda', 2021, 35000.00), -- ID será 3
('XRE 300', 'Honda', 2022, 32000.00); -- ID será 4

-- Inserindo dados na tabela Carro
INSERT INTO Carro (id, quantidade_portas, tipo_combustivel) VALUES
(1, 4, 'flex'), -- Referente ao Civic
(2, 2, 'etanol'); -- Referente ao Gol

-- Inserindo dados na tabela Moto
INSERT INTO Moto (id, cilindrada) VALUES
(3, 500), -- Referente à CB 500F
(4, 300); -- Referente à XRE 300
