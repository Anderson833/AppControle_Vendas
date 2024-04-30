package com.example.controle_de_vendas.Modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Despesas {
    ///Os atributos de despesas
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "nomeDespesa")
    private String nomeDespesa;
    @ColumnInfo(name = "data_despesas")
    private String data_despesas;
    @ColumnInfo(name = "totalDesp")
    private double totalDesp;

    public String getNomeDespesa() {
        return nomeDespesa;
    }

    public void setNomeDespesa(String nomeDespesa) {
        this.nomeDespesa = nomeDespesa;
    }

    public String getData_despesas() {
        return data_despesas;
    }

    public void setData_despesas(String data_depesas) {
        this.data_despesas = data_depesas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Despesas(){

    }
    public Despesas(int id, String nomeDespesa, String data_depesas, double totalDesp) {
        this.id = id;
        this.nomeDespesa = nomeDespesa;
        this.data_despesas = data_depesas;
        this.totalDesp = totalDesp;
    }

    public double getTotalDesp() {
        return totalDesp;
    }

    public void setTotalDesp(double totalDesp) {
        this.totalDesp = totalDesp;
    }


}
