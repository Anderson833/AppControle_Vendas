package com.example.controle_de_vendas.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.controle_de_vendas.DaoBd.MyDaoCliente;
import com.example.controle_de_vendas.DaoBd.MyDaoDespesa;
import com.example.controle_de_vendas.DaoBd.MyDaoInvestimento;
import com.example.controle_de_vendas.Modelo.Cliente;
import com.example.controle_de_vendas.Modelo.Despesas;
import com.example.controle_de_vendas.Modelo.Investimento;

@Database(entities = {Investimento.class, Despesas.class, Cliente.class},version = 1,exportSchema = false)
public abstract class MyBancoControle_venda extends RoomDatabase {

    public abstract MyDaoInvestimento myDao();
    public abstract MyDaoDespesa myDaoDespesa();
    public abstract MyDaoCliente myDaoCliente();
}