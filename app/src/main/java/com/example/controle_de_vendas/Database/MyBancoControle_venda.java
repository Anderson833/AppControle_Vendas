package com.example.controle_de_vendas.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.controle_de_vendas.DaoBd.MyDao;
import com.example.controle_de_vendas.Modelo.Investimento;

@Database(entities = Investimento.class,version = 1,exportSchema = false)
public abstract class MyBancoControle_venda extends RoomDatabase {

    public abstract MyDao myDao();
}
