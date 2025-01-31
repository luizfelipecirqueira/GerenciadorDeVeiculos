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
@WebServlet("/api/veiculos/*")
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

    /**
     * Método para listar todos os veículos.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idParam = request.getPathInfo();
        String anoParam = request.getParameter("ano");
        String modeloParam = request.getParameter("modelo");
        String fabricanteParam = request.getParameter("fabricante");
        String detalhesParam = request.getParameter("detalhes");

        if (idParam != null && idParam.startsWith("/")) {
            idParam = idParam.substring(1);
        }
        try {
            if (idParam != null && !idParam.isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam);

                    if ("true".equals(detalhesParam)) {
                        Veiculo veiculoDetalhado = veiculoService.getDetalhesDoVeiculo(id);
                        if (veiculoDetalhado != null) {
                            response.getWriter().write(new Gson().toJson(veiculoDetalhado));
                        } else {
                            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                            response.getWriter().write("{\"message\": \"Detalhes do veículo não encontrados\"}");
                        }
                    } else {
                        // Chama a busca simples
                        Veiculo veiculo = veiculoService.getVeiculoById(id);
                        if (veiculo != null) {
                            response.getWriter().write(new Gson().toJson(veiculo));
                        } else {
                            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                            response.getWriter().write("{\"message\": \"Veículo não encontrado\"}");
                        }
                    }
                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"message\": \"ID inválido\"}");
                }
            } else if (modeloParam != null && !modeloParam.isEmpty()) {
                List<Veiculo> veiculos = veiculoService.getVeiculoByModelo(modeloParam);
                if (veiculos != null && !veiculos.isEmpty()) {
                    response.getWriter().write(new Gson().toJson(veiculos));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"message\": \"Nenhum veículo encontrado com o modelo especificado\"}");
                }
            } else if (fabricanteParam != null && !fabricanteParam.isEmpty()) {
                List<Veiculo> veiculos = veiculoService.getVeiculoByFabricante(fabricanteParam);
                if (veiculos != null && !veiculos.isEmpty()) {
                    response.getWriter().write(new Gson().toJson(veiculos));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"message\": \"Nenhum veículo encontrado com o fabricante especificado\"}");
                }
            } else if (anoParam != null && !anoParam.isEmpty()) {
                int ano = Integer.parseInt(anoParam);
                if (ano != 0) {
                    Veiculo veiculo = veiculoService.getVeiculoByAno(ano);
                    if (veiculo != null) {
                        response.getWriter().write(new Gson().toJson(veiculo));
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getWriter().write("{\"message\": \"Veículo não encontrado\"}");
                    }
                }
            } else {
                List<Veiculo> veiculos = veiculoService.getAllVeiculos();
                response.getWriter().write(new Gson().toJson(veiculos));
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Erro ao processar a requisição\"}");
            e.printStackTrace();
        }
    }

    /**
     * Método para excluir um veículo específico.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idParam = request.getPathInfo();
        if (idParam != null && idParam.startsWith("/")) {
            idParam = idParam.substring(1);
        }

        try {
            int id = Integer.parseInt(idParam);
            veiculoService.deleteVeiculo(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException | SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Erro ao excluir o veículo\"}");
        }
    }

    /**
     * Método para cadastrar um novo veículo.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }

        try {
            Gson gson = new Gson();
            Veiculo veiculo = gson.fromJson(sb.toString(), Veiculo.class);
            veiculoService.saveVeiculo(veiculo);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"message\": \"Veículo cadastrado com sucesso!\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Erro ao cadastrar o veículo\"}");
        }
    }

    /**
     * Método para atualizar um veículo existente.
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idParam = request.getPathInfo();
        if (idParam != null && idParam.startsWith("/")) {
            idParam = idParam.substring(1);
        }

        try {
            int id = Integer.parseInt(idParam);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }

            Gson gson = new Gson();
            Veiculo veiculo = gson.fromJson(sb.toString(), Veiculo.class);
            veiculo.setId(id);
            veiculoService.updateVeiculo(veiculo);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"Veículo atualizado com sucesso!\"}");
        } catch (NumberFormatException | SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Erro ao atualizar o veículo\"}");
        }
    }
}
