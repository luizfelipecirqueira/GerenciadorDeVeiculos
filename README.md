# GerenciadorDeVeiculos

## Este é um CRUD desenvolvido como desafio técnico. Nesse CRUD (Create, Read, Update, Delete) tratei sobre o gerenciamento de veículos. Tenho 3 tabelas, que são Veiculo, Carro e Moto. Desenvolvi como uma aplicação Web utilizando Java (Maven) e executando no servidor Apache Tomcat. O banco de dados utilizado é o Mysql, com o SGBD (Sistema de Gerenciamento de Banco de Dados) Mysql Workbench e conexão feita por JDBC (Java Database Connectivity).

---

## Tecnologias Utilizadas

- **Java (Maven)**
- **Apache Tomcat (usei a versão 9.0.98)**
- **MySQL + MySQL Workbench (Download do SGBD versão 8.0.40 e Download do Mysql versão 8.0.40)**
- **JDBC (Java Database Connectivity - versão mysql-connector-java-8.0.29)**
- **GSON (versão gson-2.8.8)**
- **JDK 23**
- **NetBeans (IDE que utilizei para o projeto)**

## **Configuração do Projeto**

### 
**Instale o mysql e o Workbench**
**Crie o banco de dados - no meu caso chamei de gerenciador de veículos**
**Utilize o script abaixo, que já contêm as inserções necessárias**

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


# Configuração do Projeto no Netbeans #
**Baixe e instale o Apache Tomcat**
**No Netbeans vá em Tools -> Servers -> Add Server -> Escolha Apache Tomcat -> Server Location -> Localize o arquivo do Tomcat e inclua ele -> Depois Finish** 

# Para rodar o Projeto #
**Clique com o direito no projeto -> Clean and Build -> Run**


# Projeto Desenvolvido por Luiz Felipe C. dos Santos
