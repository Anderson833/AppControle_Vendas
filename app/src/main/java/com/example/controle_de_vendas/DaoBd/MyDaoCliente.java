package com.example.controle_de_vendas.DaoBd;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.controle_de_vendas.Modelo.Cliente;

import java.util.List;

@Dao
public interface MyDaoCliente {
 //Métodos que vão manipular os dados de cliente no banco de dados
    @Insert
    void inserirCliente(Cliente cliente); // vai inserir os dados
    @Update
    void updateCliente(Cliente cliente);  // vai alterar os dados
    @Delete
    void deleteCliente(int id);// vai deletar os dados

    //-------------daqui pra baixo vai se criados os métodos de pesquisar em relação ao cliente ---------
    // Método de lista os clientes entre datas
    @Query("SELECT * FROM Cliente WHERE data>=:datai and data<=:dataf")
    List<Cliente> listaClienteEntreDatas(String datai,String dataf);
    // Método para lista os dados de cliente especifico por fone ou nome ou endereço
    @Query("SELECT * FROM Cliente WHERE telefone=:fone or nomecliente=:nome or endereco=:endereco")
    List<Cliente> listaClientePorFoneOuNomeOuEndereco(String fone,String nome,String endereco);
}
