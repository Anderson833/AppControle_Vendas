package com.example.controle_de_vendas;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.controle_de_vendas.DaoBd.MyDao;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.Modelo.Investimento;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private int id;
   private MyBancoControle_venda bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.Recycleviewprodutos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


                 lista();

      //  updateInvestimentos();

        Log.i("Main","Main lista");
    }

           public void lista () {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();
                    MyDao myDao = bd.myDao();
                    List<Investimento> listInvestir = myDao.getAllInvestimentos();
                    Adapter adapter = new Adapter(listInvestir);
                    recyclerView.setAdapter(adapter);
                }
            }).start();
        }

    public void updateDados(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int id = getIntent().getIntExtra("id", 0);
                int quantidade = getIntent().getIntExtra("qtd", 0);
                String nome = getIntent().getStringExtra("nome");
                String data = getIntent().getStringExtra("data");
                double precoRv = getIntent().getDoubleExtra("precoRv", 0);
                double valorPg = getIntent().getDoubleExtra("valorPg", 0);
                double total = getIntent().getDoubleExtra("total", 0);
                bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").allowMainThreadQueries().build();
                MyDao myDao = bd.myDao();
                // myDao.updateInvestimentos(nome,data,quantidade,precoRv,valorPg,total,id);
                myDao.alteraDados(new Investimento(id,nome,data,quantidade,precoRv,valorPg,total));
            }
        }).start();

    }

    }
