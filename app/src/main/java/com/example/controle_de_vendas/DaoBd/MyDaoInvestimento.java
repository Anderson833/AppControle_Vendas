package com.example.controle_de_vendas.DaoBd;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.controle_de_vendas.Modelo.Investimento;

import java.util.List;

/**
 * Essa classe vai realizar a criação dos métodos de manipulação do banco de dados
 */
@Dao
public interface MyDaoInvestimento {

       // Método para inserir os dados de investimentos no banco de dados
    @Insert
    void insertInvetimentos(Investimento investimento);
    //Método para deleta um registro do banco de dados passando um id
    @Query("DELETE FROM Investimento WHERE id=:id")
    void deletaDados(int id);

    //Método para alterar os dados de um objeto do tipo investimneto no banco de dados
    @Update
    void alteraDados(Investimento investimento);

    // Método para soma todoo o total da coluna total do banco de dados
    @Query("SELECT SUM(total) FROM Investimento")
    double todoTotalInvestido();

    //Método para saber e lista um id se existe id salvo
    @Query("SELECT EXISTS(SELECT*FROM Investimento WHERE id=:id)")
    Boolean is_exist(int id);

    // Método para lista todos os dados que tem no banco de dados
    @Query("SELECT*FROM Investimento")
    List<Investimento> getAllInvestimentos();

    //Método para mostrar a quantidade de registros
    @Query("SELECT COUNT(Id) FROM Investimento")
    String qtdRegistros();

    // Método para lista todos os dados que tem no banco de dados pela data
    @Query("SELECT*FROM Investimento WHERE data=:data")
    List<Investimento> getAllInvestimentosPelaData(String data);
    //Método para mostrar a quantidade de registros pela data
    @Query("SELECT COUNT(Id) FROM Investimento WHERE data=:dat")
    String qtdRegistrosPelaData(String dat);
    // Método para soma todoo o total da coluna total do banco de dados pela data
    @Query("SELECT SUM(total) FROM Investimento WHERE data=:dat")
    double totalInvestidoPelaData(String dat);

// Na linha de baixo tem os métodos para manupulas os dados em relação a produtos

    // Método para lista todos nomes dos produtos que tem no banco de dados
    @Query("SELECT DISTINCT nomeProd FROM Investimento")
    List<String> listaProdutosNomes();
    // Método para lista todos investimentos pelo nome do produto
    @Query("SELECT * FROM Investimento WHERE nomeProd=:nome")
    List<Investimento> listaTodosProdPeloNome(String nome);
    //Método para mostrar a quantidade de registros pelo nome
    @Query("SELECT COUNT(Id) FROM Investimento WHERE nomeProd=:nom")
    String qtdRegistrosPeloNomeProd(String nom);
    // Método para soma todoo o total da coluna total do banco de dados pelo no nome do produto
    @Query("SELECT SUM(total) FROM Investimento WHERE nomeProd=:nameProd")
    double totalInvestidoPeloNomeProd(String nameProd);
//------------------Daqui para baixo e métodos entre as datas --------------------------------------------
    // Método para lista todos investimentos entre as datas
    @Query("SELECT * FROM Investimento WHERE data>=:datai and data<=:dataf")
    List<Investimento> listaTodosEntreDatas(String datai,String dataf);
    //Método para mostrar a quantidade de registros entre asa datas
    @Query("SELECT COUNT(Id) FROM Investimento WHERE data>=:datai and data<=:dataf")
    String qtdRegistrosEntreDatas(String datai,String dataf);
    // Método para soma todoo o total da coluna total do banco de dados entre asa datas
    @Query("SELECT SUM(total) FROM Investimento WHERE data>=:datai and data<=:dataf")
    double totalInvestidoEntreDatas(String datai,String dataf);

    //------------------------daqui para baixo métodos de data com produto------------------------------------
// Métodos para lista os investimentos por uma data e produto especifico
    // Método para lista todos investimentos entre uma data e produto
    @Query("SELECT * FROM Investimento WHERE data=:data and nomeProd=:produto")
    List<Investimento> listaTodosEntreDataProduto(String data,String produto);
    //Método para mostrar a quantidade de registros entre data e produto
    @Query("SELECT COUNT(Id) FROM Investimento WHERE data=:data and nomeProd=:nomeProduto")
    String qtdRegistrosEntreDataProduto(String data,String nomeProduto);
    // Método para soma todoo o total da coluna total do banco de dados entre data e produto
    @Query("SELECT SUM(total) FROM Investimento WHERE data=:data and nomeProd=:nomeProduto")
    double totalInvestidoEntreDataProduto(String data,String nomeProduto);
    //---------------------Daqui para baixo os métodos são de lista, qtd de registros e soma entre datas e produto ------------------------------
    // Método para lista todos investimentos entre as datas e produto
    @Query("SELECT * FROM Investimento WHERE data>=:datai and data<=:dataf and nomeProd=:produto")
    List<Investimento> listaTodosEntreDatas_e_Produto(String datai,String dataf,String produto);
    //Método para mostrar a quantidade de registros entre datas e produto
    @Query("SELECT COUNT(Id) FROM Investimento WHERE data>=:datai and data<=:dataf and nomeProd=:nomeProduto")
    String qtdRegistrosEntreDatas_e_Produto(String datai,String dataf,String nomeProduto);
    // Método para soma todoo o total da coluna total do banco de dados entre datas e produto
    @Query("SELECT SUM(total) FROM Investimento WHERE data>=:datai and data<=:dataf and nomeProd=:nomeProduto")
    double totalInvestidoEntreDatas_e_Produto(String datai,String dataf,String nomeProduto);
}
