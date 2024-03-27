package com.example.controle_de_vendas.Modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * ESSA CLASSE VAI MODELA CRIANDO UMA TABELA DE INVESTIMENTO
 */
@Entity
public class Investimento {
    // OS ATRIBUTOS
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "nomeProd")
    private String nomeProd;
    @ColumnInfo(name = "data")
    private String data;
    @ColumnInfo(name = "quantidade")
    private int quantidade;
    @ColumnInfo(name = "valorRv")
    private double valorRv;
    @ColumnInfo(name = "precoPg")
    private double precoPg;
    @ColumnInfo(name = "total")
    private double todoTotal;

    public Investimento() {
    }
    public Investimento(int id, String nomeProd, String data, int quantidade, double valorRv, double precoPg,double total) {
        this.id = id;
        this.nomeProd = nomeProd;
        this.data = data;
        this.quantidade = quantidade;
        this.valorRv = valorRv;
        this.precoPg = precoPg;
        this.todoTotal=total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeProd() {
        return nomeProd;
    }

    public void setNomeProd(String nomeProd) {
        this.nomeProd = nomeProd;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorRv() {
        return valorRv;
    }

    public void setValorRv(double valorRv) {
        this.valorRv = valorRv;
    }

    public double getPrecoPg() {
        return precoPg;
    }

    public void setPrecoPg(double precoPg) {
        this.precoPg = precoPg;
    }

    public double getTodoTotal() {
        return todoTotal;
    }

    public void setTodoTotal(double todoTotal) {
        this.todoTotal = todoTotal;
    }
}
