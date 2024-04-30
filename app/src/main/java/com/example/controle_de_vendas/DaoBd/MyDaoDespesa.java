package com.example.controle_de_vendas.DaoBd;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.controle_de_vendas.Modelo.Despesas;

import java.util.List;
@Dao
public interface MyDaoDespesa {
    // Método para inserir os dados de despesa no banco de dados
    @Insert
    void insertDespesas(Despesas desp);
    //Método para deleta um registro do banco de dados passando um id
    @Query("DELETE FROM Despesas WHERE id=:id")
    void deletaUmRgDespesa(int id);
    //Método para alterar os dados de um objeto do tipo Despesas no banco de dados
    @Update
    void alteraDadosDespesa(Despesas despesas);
    //Método para saber e lista um id se existe id salvo
    @Query("SELECT EXISTS(SELECT*FROM Despesas WHERE id=:id)")
    Boolean is_exist(int id);
    // Método para lista todos os dados que tem no banco de dados
    @Query("SELECT*FROM Despesas")
    List<Despesas> getAllDespesas();
    //Método para mostrar a quantidade de registros
    @Query("SELECT COUNT(Id) FROM Despesas")
    String qtdRegistrosDespesas();
    // Método para soma todoo o total da coluna total do banco de dados
    @Query("SELECT SUM(totalDesp) FROM despesas")
    double todoTotalDespesas();
    // Método para lista todos nomes das despesas que tem no banco de dados
    @Query("SELECT DISTINCT nomeDespesa FROM Despesas")
    List<String> listaNomesDespesas();
    //--------------------DAQUI PRA BAIXO TEM AS FUNÇÕES DE DESPESAS COM UMA DATA-------------
    // Método para lista todas as despesas que tem no banco de dados pela data
    @Query("SELECT*FROM Despesas WHERE data_despesas=:data")
    List<Despesas> getAllDespesasPelaData(String data);
    @Query("SELECT COUNT(Id) FROM Despesas WHERE data_despesas=:dat")
    String qtdRegistrosDespesaData(String dat);
    // Método para soma todoo o total da coluna total do banco de dados pela data
    @Query("SELECT SUM(totalDesp) FROM Despesas WHERE data_despesas=:dat")
    double totalDespesaPelaData(String dat);
//-----------------------------------------------------------------------------------------------
//--------------------DAQUI PRA BAIXO TEM AS FUNÇÕES DE DESPESAS COM DESCRIÇÃO-------------
// Método para lista todas as despesas que tem no banco de dados pela descrição
@Query("SELECT*FROM Despesas WHERE nomeDespesa=:descricao")
List<Despesas> getAllDespesasPelaDescricao(String descricao);
    @Query("SELECT COUNT(Id) FROM Despesas WHERE nomeDespesa=:desc")
    String qtdRegistrosDespesaPelaDescricao(String desc);
    // Método para soma todoo o total da coluna total do banco de dados pela data
    @Query("SELECT SUM(totalDesp) FROM Despesas WHERE nomeDespesa=:nomeDesc")
    double totalInvestidoPelaDescricao(String nomeDesc);
//-----------------------------------------------------------------------------------------------
  //------------------Daqui para baixo e métodos entre as datas --------------------------------------------
    // Método para lista todas DESPESAS entre as datas
    @Query("SELECT * FROM Despesas WHERE data_despesas>=:datai and data_despesas<=:dataf")
    List<Despesas> listaTodosDespesasEntreDatas(String datai,String dataf);
    //Método para mostrar a quantidade de registros entre asa datas
    @Query("SELECT COUNT(Id) FROM Despesas WHERE data_despesas>=:datai and data_despesas<=:dataf")
    String qtdRegistrosDespesasEntreDatas(String datai,String dataf);
    // Método para soma todoo o total da coluna total do banco de dados entre as datas
    @Query("SELECT SUM(totalDesp) FROM Despesas WHERE data_despesas>=:datai and data_despesas<=:dataf")
    double totalDespesasEntreDatas(String datai,String dataf);
    //----------------------Daqui pra baixo estão as funções de despesas entre as datas-----------------------
    @Query("SELECT * FROM Despesas WHERE data_despesas>=:datai and data_despesas<=:dataf and nomeDespesa=:descricao")
    List<Despesas> listaTodosDespesasEntreDatas(String datai,String dataf,String descricao);
    //Método para mostrar a quantidade de registros de despesas entre datas
    @Query("SELECT COUNT(Id) FROM Despesas WHERE data_despesas>=:datai and data_despesas<=:dataf and nomeDespesa=:nomeDespesa")
    String qtdRegistrosDespesasEntreDatas(String datai,String dataf,String nomeDespesa);
    // Método para soma todoo o total da coluna total do banco de dados de despesas entre datas
    @Query("SELECT SUM(totalDesp) FROM Despesas WHERE data_despesas>=:datai and data_despesas<=:dataf and nomeDespesa=:descricao")
    double totalDescricaoEntreDatas(String datai,String dataf,String descricao);
//------------------------------------------------------------------------------------------------------------------------
}
