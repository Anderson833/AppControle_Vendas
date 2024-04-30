package com.example.controle_de_vendas.MainInvestimentos;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.controle_de_vendas.DaoBd.MyDaoInvestimento;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.Modelo.Investimento;
import com.example.controle_de_vendas.databinding.ActivityListaPorProdutoBinding;

import java.text.DecimalFormat;
import java.util.List;

public class ListaPorProduto extends AppCompatActivity {

    private ActivityListaPorProdutoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityListaPorProdutoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerviewProdutoEspecifico.setLayoutManager(layoutManager);
        binding.recyclerviewProdutoEspecifico.setHasFixedSize(true);
        listaPeloNomeProdut(getIntent().getStringExtra("nomeProd"));
    }
    public String formataValor(double valor){
        //  classe DecimaFormat para colocar os valores em casas decimais
        DecimalFormat decimalFormat  = new DecimalFormat("#,##0.00");
        String valorConvertido=decimalFormat.format(valor);
        return valorConvertido;
    }
    public void listaPeloNomeProdut (String nome) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MyBancoControle_venda bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();
                    MyDaoInvestimento myDao = bd.myDao();
                    List<Investimento> listInvestir = myDao.listaTodosProdPeloNome(nome);
                    Adapter adapter = new Adapter(listInvestir);
                    binding.recyclerviewProdutoEspecifico.setAdapter(adapter);
                    String qtdRg=myDao.qtdRegistrosPeloNomeProd(nome);
                    binding.totalListaRg.setText(""+qtdRg);
                    double total=myDao.totalInvestidoPeloNomeProd(nome);
                    binding.totalLista.setText(""+formataValor(total));
                    binding.nomeProduto.setText(nome);
                }catch (Exception t){
                }
            }
        }).start();
    }
}