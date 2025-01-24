/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gerenciadordeveiculos.dao;

import com.gerenciadordeveiculos.model.Veiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuário
 */
public class VeiculoDAO {
    private Connection connection;

    public VeiculoDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para salvar um veículo (geral)
    public void save(Veiculo veiculo) throws SQLException {
        String sql = "INSERT INTO Veiculo (modelo, fabricante, ano, preco) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, veiculo.getModelo());
            stmt.setString(2, veiculo.getFabricante());
            stmt.setInt(3, veiculo.getAno());
            stmt.setBigDecimal(4, veiculo.getPreco());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                veiculo.setId(id);
            }
        }
    }
    
    public List<Veiculo> findAll() throws SQLException {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM Veiculo";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Veiculo veiculo = new Veiculo(
                        rs.getInt("id"),
                        rs.getString("modelo"),
                        rs.getString("fabricante"),
                        rs.getInt("ano"),
                        rs.getBigDecimal("preco")
                );
                veiculos.add(veiculo);
            }
        }
        return veiculos;
    }

    public Veiculo findById(int id) throws SQLException {
        String sql = "SELECT * FROM Veiculo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Veiculo veiculo = new Veiculo(
                        rs.getInt("id"),
                        rs.getString("modelo"),
                        rs.getString("fabricante"),
                        rs.getInt("ano"),
                        rs.getBigDecimal("preco")
                );
                return veiculo;
            }
        }
        return null;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Veiculo WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void update(Veiculo veiculo) throws SQLException {
        String sql = "UPDATE Veiculo SET modelo = ?, fabricante = ?, ano = ?, preco = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getModelo());
            stmt.setString(2, veiculo.getFabricante());
            stmt.setInt(3, veiculo.getAno());
            stmt.setBigDecimal(4, veiculo.getPreco());
            stmt.setInt(5, veiculo.getId());
            stmt.executeUpdate();
        }
    }
}