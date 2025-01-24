/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gerenciadordeveiculos.model;

import java.math.BigDecimal;

/**
 *
 * @author Usuário
 */

public class Carro extends Veiculo {
    private int quantidadePortas;
    private String tipoCombustivel;


    public Carro(int id, String modelo, String fabricante, int ano, BigDecimal preco, int quantidadePortas, String tipoCombustivel) {
        super(id, modelo, fabricante, ano, preco);
        this.quantidadePortas = quantidadePortas;
        this.tipoCombustivel = tipoCombustivel;
    }

    // Getters e setters
    public int getQuantidadePortas() {
        return quantidadePortas;
    }

    public void setQuantidadePortas(int quantidadePortas) {
        this.quantidadePortas = quantidadePortas;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }
}

