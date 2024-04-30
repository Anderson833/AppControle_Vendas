package com.example.controle_de_vendas.Modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * CLASSE PARA FAZE A MODELAGEM DE CLIENTE
 */
@Entity
public class Cliente {
    //ATRIBUTOS DO TIPO CLIENTE
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "nomecliente")
    private String nomeCliente;
    @ColumnInfo(name = "endereco")
    private String endereco;
    @ColumnInfo(name = "data")
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Cliente(int id, String nomeCliente, String endereco, String telefone, String data) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.endereco = endereco;
        this.telefone = telefone;
        this.data=data;
    }

    @ColumnInfo(name = "telefone")
    private String telefone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
