/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gerenciadordeveiculos.dao;

import com.gerenciadordeveiculos.model.Moto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Usuário
 */

public class MotoDAO {
    private Connection connection;

    public MotoDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(Moto moto) throws SQLException {
        String sqlVeiculo = "INSERT INTO Veiculo (modelo, fabricante, ano, preco) VALUES (?, ?, ?, ?)";
        String sqlMoto = "INSERT INTO Moto (id, cilindrada) VALUES (?, ?)";

        try (PreparedStatement stmtVeiculo = connection.prepareStatement(sqlVeiculo, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtMoto = connection.prepareStatement(sqlMoto)) {

            stmtVeiculo.setString(1, moto.getModelo());
            stmtVeiculo.setString(2, moto.getFabricante());
            stmtVeiculo.setInt(3, moto.getAno());
            stmtVeiculo.setBigDecimal(4, moto.getPreco());
            stmtVeiculo.executeUpdate();

            ResultSet generatedKeys = stmtVeiculo.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idVeiculo = generatedKeys.getInt(1);
                moto.setId(idVeiculo);

                stmtMoto.setInt(1, moto.getId());
                stmtMoto.setInt(2, moto.getCilindrada());
                stmtMoto.executeUpdate();
            }
        }
    }

    public Moto findById(int id) throws SQLException {
        String sqlVeiculo = "SELECT * FROM Veiculo WHERE id = ?";
        String sqlMoto = "SELECT * FROM Moto WHERE id = ?";

        try (PreparedStatement stmtVeiculo = connection.prepareStatement(sqlVeiculo);
             PreparedStatement stmtMoto = connection.prepareStatement(sqlMoto)) {

            stmtVeiculo.setInt(1, id);
            ResultSet rsVeiculo = stmtVeiculo.executeQuery();

            if (rsVeiculo.next()) {
                Moto moto = new Moto(
                        rsVeiculo.getInt("id"),
                        rsVeiculo.getString("modelo"),
                        rsVeiculo.getString("fabricante"),
                        rsVeiculo.getInt("ano"),
                        rsVeiculo.getBigDecimal("preco"),
                        rsVeiculo.getInt("cilindrada")
                );

                stmtMoto.setInt(1, id);
                ResultSet rsMoto = stmtMoto.executeQuery();

                if (rsMoto.next()) {
                    moto.setCilindrada(rsMoto.getInt("cilindrada"));
                }

                return moto;
            }
        }
        return null;
    }

    public void delete(int id) throws SQLException {
        String sqlMoto = "DELETE FROM Moto WHERE id = ?";
        String sqlVeiculo = "DELETE FROM Veiculo WHERE id = ?";

        try (PreparedStatement stmtMoto = connection.prepareStatement(sqlMoto);
             PreparedStatement stmtVeiculo = connection.prepareStatement(sqlVeiculo)) {

            stmtMoto.setInt(1, id);
            stmtMoto.executeUpdate();

            stmtVeiculo.setInt(1, id);
            stmtVeiculo.executeUpdate();
        }
    }

    public void update(Moto moto) throws SQLException {
        String sqlVeiculo = "UPDATE Veiculo SET modelo = ?, fabricante = ?, ano = ?, preco = ? WHERE id = ?";
        String sqlMoto = "UPDATE Moto SET cilindrada = ? WHERE id = ?";

        try (PreparedStatement stmtVeiculo = connection.prepareStatement(sqlVeiculo);
             PreparedStatement stmtMoto = connection.prepareStatement(sqlMoto)) {

            stmtVeiculo.setString(1, moto.getModelo());
            stmtVeiculo.setString(2, moto.getFabricante());
            stmtVeiculo.setInt(3, moto.getAno());
            stmtVeiculo.setBigDecimal(4, moto.getPreco());
            stmtVeiculo.setInt(5, moto.getId());
            stmtVeiculo.executeUpdate();

            stmtMoto.setInt(1, moto.getCilindrada());
            stmtMoto.setInt(2, moto.getId());
            stmtMoto.executeUpdate();
        }
    }
}
