/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gerenciadordeveiculos.service;

import com.gerenciadordeveiculos.dao.VeiculoDAO;
import com.gerenciadordeveiculos.model.Veiculo;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Usuário
 */
public class VeiculoService {

    private VeiculoDAO veiculoDAO;

    public VeiculoService(Connection connection) {
        this.veiculoDAO = new VeiculoDAO(connection);
    }

    public void saveVeiculo(Veiculo veiculo) throws SQLException {
        veiculoDAO.save(veiculo);
    }

    public List<Veiculo> getAllVeiculos() throws SQLException {
        return veiculoDAO.findAll();
    }

    public Veiculo getVeiculoById(int id) throws SQLException {
        return veiculoDAO.findById(id);
    }
    
    public Veiculo getVeiculoByAno(int ano) throws SQLException {
        return veiculoDAO.findByAno(ano);
    }

    public List<Veiculo> getVeiculoByModelo(String modelo) throws SQLException {
        return veiculoDAO.findByModelo(modelo);
    }
    
    public List<Veiculo> getVeiculoByFabricante(String fabricante) throws SQLException{
        return veiculoDAO.findByFabricante(fabricante);
    }

    public void deleteVeiculo(int id) throws SQLException {
        veiculoDAO.delete(id);
    }

    public void updateVeiculo(Veiculo veiculo) throws SQLException {
        veiculoDAO.update(veiculo);
    }
    
    public Veiculo getDetalhesDoVeiculo(int id) throws SQLException{
        return veiculoDAO.findDetalhesDoVeiculo(id);
    }
}
