package com.example.controle_de_vendas.MainInvestimentos;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.controle_de_vendas.DaoBd.MyDaoInvestimento;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.Modelo.Investimento;
import com.example.controle_de_vendas.R;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView totalLista,totalListaRg;
   private MyBancoControle_venda bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.Recycleviewprodutos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
       // Nas linha abaixo tem 2 TextView para mostrar todor total e todor registros
        totalListaRg = findViewById(R.id.totalListaRg);
        totalLista=findViewById(R.id.totalLista);
        listaTodosInvestimentos();// MÉTODO QUE LISTA TODOS DADOS
    }
    public String formataValor(double valor){
        //  classe DecimaFormat para colocar os valores em casas decimais
        DecimalFormat decimalFormat  = new DecimalFormat("#,##0.00");
            String valorConvertido=decimalFormat.format(valor);
        return valorConvertido;
    }
    public void lista () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();
                    MyDaoInvestimento myDao = bd.myDao();
                    List<Investimento> listInvestir = myDao.getAllInvestimentos();
                    Adapter adapter = new Adapter(listInvestir);
                    recyclerView.setAdapter(adapter);
                }catch (Exception e){
                }
            }
        }).start();
    }
    public void setaTotal(){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 try {
                     bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").allowMainThreadQueries().build();
                     MyDaoInvestimento myDao = bd.myDao();
                     double total=  myDao.todoTotalInvestido();
                     totalLista.setText(formataValor(total));
                 }catch (Exception r){
                 }
             }
         }).start();
    }
    public void setaQtdRegistros(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").allowMainThreadQueries().build();
                    MyDaoInvestimento myDao = bd.myDao();
                    String qtdRg=  myDao.qtdRegistros();
                    totalListaRg.setText(""+qtdRg);
                }catch (Exception e){
                }
            }
        }).start();
    }
      public  void listaTodosInvestimentos(){
          lista();
          setaTotal();
          setaQtdRegistros();
      }

    }
