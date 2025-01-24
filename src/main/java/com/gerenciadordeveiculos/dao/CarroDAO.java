/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gerenciadordeveiculos.dao;

import com.gerenciadordeveiculos.model.Carro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Usuário
 */
public class CarroDAO {
    private Connection connection;

    public CarroDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para salvar um carro
    public void save(Carro carro) throws SQLException {
        String sqlVeiculo = "INSERT INTO Veiculo (modelo, fabricante, ano, preco) VALUES (?, ?, ?, ?)";
        String sqlCarro = "INSERT INTO Carro (id, quantidade_portas, tipo_combustivel) VALUES (?, ?, ?)";

        try (PreparedStatement stmtVeiculo = connection.prepareStatement(sqlVeiculo, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtCarro = connection.prepareStatement(sqlCarro)) {

            // Inserção na tabela Veiculo
            stmtVeiculo.setString(1, carro.getModelo());
            stmtVeiculo.setString(2, carro.getFabricante());
            stmtVeiculo.setInt(3, carro.getAno());
            stmtVeiculo.setBigDecimal(4, carro.getPreco());
            stmtVeiculo.executeUpdate();

            // Recupera o ID gerado para o veículo
            ResultSet generatedKeys = stmtVeiculo.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idVeiculo = generatedKeys.getInt(1);
                carro.setId(idVeiculo);

                // Inserção na tabela Carro
                stmtCarro.setInt(1, carro.getId());
                stmtCarro.setInt(2, carro.getQuantidadePortas());
                stmtCarro.setString(3, carro.getTipoCombustivel());
                stmtCarro.executeUpdate();
            }
        }
    }

    public Carro findById(int id) throws SQLException {
        String sqlVeiculo = "SELECT * FROM Veiculo WHERE id = ?";
        String sqlCarro = "SELECT * FROM Carro WHERE id = ?";

        try (PreparedStatement stmtVeiculo = connection.prepareStatement(sqlVeiculo);
             PreparedStatement stmtCarro = connection.prepareStatement(sqlCarro)) {

            stmtVeiculo.setInt(1, id);
            ResultSet rsVeiculo = stmtVeiculo.executeQuery();

            if (rsVeiculo.next()) {
                Carro carro = new Carro(
                        rsVeiculo.getInt("id"),
                        rsVeiculo.getString("modelo"),
                        rsVeiculo.getString("fabricante"),
                        rsVeiculo.getInt("ano"),
                        rsVeiculo.getBigDecimal("preco"),
                        rsVeiculo.getInt("quantidadePortas"),
                        rsVeiculo.getString("tipoCombustivel")
                );

                stmtCarro.setInt(1, id);
                ResultSet rsCarro = stmtCarro.executeQuery();

                if (rsCarro.next()) {
                    carro.setQuantidadePortas(rsCarro.getInt("quantidade_portas"));
                    carro.setTipoCombustivel(rsCarro.getString("tipo_combustivel"));
                }

                return carro;
            }
        }
        return null;
    }

    public void delete(int id) throws SQLException {
        String sqlCarro = "DELETE FROM Carro WHERE id = ?";
        String sqlVeiculo = "DELETE FROM Veiculo WHERE id = ?";

        try (PreparedStatement stmtCarro = connection.prepareStatement(sqlCarro);
             PreparedStatement stmtVeiculo = connection.prepareStatement(sqlVeiculo)) {

            stmtCarro.setInt(1, id);
            stmtCarro.executeUpdate();

            stmtVeiculo.setInt(1, id);
            stmtVeiculo.executeUpdate();
        }
    }

    public void update(Carro carro) throws SQLException {
        String sqlVeiculo = "UPDATE Veiculo SET modelo = ?, fabricante = ?, ano = ?, preco = ? WHERE id = ?";
        String sqlCarro = "UPDATE Carro SET quantidade_portas = ?, tipo_combustivel = ? WHERE id = ?";

        try (PreparedStatement stmtVeiculo = connection.prepareStatement(sqlVeiculo);
             PreparedStatement stmtCarro = connection.prepareStatement(sqlCarro)) {

            stmtVeiculo.setString(1, carro.getModelo());
            stmtVeiculo.setString(2, carro.getFabricante());
            stmtVeiculo.setInt(3, carro.getAno());
            stmtVeiculo.setBigDecimal(4, carro.getPreco());
            stmtVeiculo.setInt(5, carro.getId());
            stmtVeiculo.executeUpdate();

            stmtCarro.setInt(1, carro.getQuantidadePortas());
            stmtCarro.setString(2, carro.getTipoCombustivel());
            stmtCarro.setInt(3, carro.getId());
            stmtCarro.executeUpdate();
        }
    }
}
