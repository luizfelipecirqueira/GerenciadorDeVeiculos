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
public class Moto extends Veiculo {
    private int cilindrada;


    public Moto(int id, String modelo, String fabricante, int ano, BigDecimal preco, int cilindrada) {
        super(id, modelo, fabricante, ano, preco);
        this.cilindrada = cilindrada;
    }

    // Getters e setters
    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }
}
