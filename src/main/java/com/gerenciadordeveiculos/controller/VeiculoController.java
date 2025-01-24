/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gerenciadordeveiculos.controller;

import com.gerenciadordeveiculos.model.Veiculo;
import com.gerenciadordeveiculos.service.VeiculoService;
import com.gerenciadordeveiculos.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import com.google.gson.Gson;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Usuário
 */
@WebServlet("/api/veiculos/*")  // Alterando o mapeamento para capturar o ID na URL, quando precisar pegar
public class VeiculoController extends HttpServlet {

    private VeiculoService veiculoService;

    public VeiculoController() {
        try {
            Connection connection = DBConnection.getConnection();
            this.veiculoService = new VeiculoService(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");  // Permite qualquer origem
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Veiculo> veiculos = veiculoService.getAllVeiculos(); // Pega os veículos do serviço
            String json = new Gson().toJson(veiculos); // Converte a lista de veículos para JSON
            response.getWriter().write(json); // Envia o JSON como resposta
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Erro ao buscar veículos\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Pegando o ID da URL
        String idParam = request.getPathInfo();  // Exemplo: /api/veiculos/id-veiculo
        if (idParam != null && idParam.startsWith("/")) {
            idParam = idParam.substring(1);  // Remove a barra inicial para pegar o ID
        }

        try {
            int id = Integer.parseInt(idParam);
            veiculoService.deleteVeiculo(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);  // Status 204 - sucesso, porém sem conteúdo.
        } catch (NumberFormatException | SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Erro ao excluir o veículo\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Lê o JSON enviado no corpo da requisição
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }

        try {
            // Converte o JSON para um objeto Veiculo
            Gson gson = new Gson();
            Veiculo veiculo = gson.fromJson(sb.toString(), Veiculo.class);

            // Salva o veículo no banco de dados
            veiculoService.saveVeiculo(veiculo);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"message\": \"Veículo cadastrado com sucesso!\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Erro ao cadastrar o veículo\"}");
        }
    }
}
