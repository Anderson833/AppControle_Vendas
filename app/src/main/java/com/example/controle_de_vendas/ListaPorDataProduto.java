package com.example.controle_de_vendas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.controle_de_vendas.DaoBd.MyDao;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.Modelo.Investimento;
import com.example.controle_de_vendas.databinding.ActivityListaPorDataProdutoBinding;

import java.text.DecimalFormat;
import java.util.List;

public class ListaPorDataProduto extends AppCompatActivity {

    private ActivityListaPorDataProdutoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityListaPorDataProdutoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerviewProdutoEspecifico.setLayoutManager(layoutManager);
        binding.recyclerviewProdutoEspecifico.setHasFixedSize(true);
         String data=getIntent().getStringExtra("dataunica");
         String nomeProduto=getIntent().getStringExtra("nomeProd");
         listaPelaDataProduto(data,nomeProduto);
    }

    public void listaPelaDataProduto (String data,String nome) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MyBancoControle_venda bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();
                    MyDao myDao = bd.myDao();
                    List<Investimento> listInvestir = myDao.listaTodosEntreDataProduto(data,nome);
                    Adapter adapter = new Adapter(listInvestir);
                    binding.recyclerviewProdutoEspecifico.setAdapter(adapter);
                    String qtdRg=myDao.qtdRegistrosEntreDataProduto(data,nome);
                    binding.totalListaRg.setText(""+qtdRg);
                    double total=myDao.totalInvestidoEntreDataProduto(data,nome);
                    binding.totalLista.setText(""+formataValor(total));
                    binding.nomeProduto.setText(nome);
                    binding.editDataEspecificaProd.setText(data);
                }catch (Exception e){

                }


            }
        }).start();
    }
    public String formataValor(double valor){
        //  classe DecimaFormat para colocar os valores em casas decimais
        DecimalFormat decimalFormat  = new DecimalFormat("#,##0.00");
        String valorConvertido=decimalFormat.format(valor);
        return valorConvertido;
    }
}