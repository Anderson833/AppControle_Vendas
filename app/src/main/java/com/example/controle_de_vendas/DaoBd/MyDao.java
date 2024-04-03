package com.example.controle_de_vendas.DaoBd;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.controle_de_vendas.Modelo.Investimento;

import java.util.List;

/**
 * Essa classe vai realizar a criação dos métodos de manipulação do banco de dados
 */
@Dao
public interface MyDao {
    @Insert
    void insertInvetimentos(Investimento investimento);

   // @Query("UPDATE Investimento SET nomeProd=:n,data=:dat, quantidade=:quant, valorRv =:vrv ,precoPg =:ppg,total=:ttl WHERE id = :id")
    //void updateInvestimentos(String n,String dat,int quant,double vrv,double ppg,double ttl,int id);

    @Query("DELETE FROM Investimento WHERE id=:id")
    void deletaDados(int id);
    @Update
    void alteraDados(Investimento investimento);

    @Query("SELECT SUM(total) FROM Investimento")
    double todoTotalInvestido();

    @Delete
    void deleteInvestimentos(Investimento investimento);
    @Query("SELECT EXISTS(SELECT*FROM Investimento WHERE id=:id)")
    Boolean is_exist(int id);
    @Query("SELECT*FROM Investimento")
    List<Investimento> getAllInvestimentos();

}
