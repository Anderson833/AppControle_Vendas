package com.example.controle_de_vendas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.controle_de_vendas.DaoBd.MyDao;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.Modelo.Investimento;
import com.example.controle_de_vendas.databinding.ActivityListaEntreDatasProdutoBinding;

import java.text.DecimalFormat;
import java.util.List;

public class ListaEntreDatasProduto extends AppCompatActivity {

    private ActivityListaEntreDatasProdutoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityListaEntreDatasProdutoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.RecycleviewprodutosEntreDatas.setLayoutManager(layoutManager);
        binding.RecycleviewprodutosEntreDatas.setHasFixedSize(true);
        String datacomeco=getIntent().getStringExtra("datacomeco");
        String dataTernino=getIntent().getStringExtra("datatermino");
        String nome_produto=getIntent().getStringExtra("nomeProduto");
        listaProdutoEntreDatas(datacomeco,dataTernino,nome_produto);
    }
    public void listaProdutoEntreDatas (String datai,String dataf,String nomeProd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MyBancoControle_venda bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();
                    MyDao myDao = bd.myDao();
                    List<Investimento> listInvestir = myDao.listaTodosEntreDatas_e_Produto(datai,dataf,nomeProd);
                    Adapter adapter = new Adapter(listInvestir);
                    binding.RecycleviewprodutosEntreDatas.setAdapter(adapter);
                    String qtdRg=myDao.qtdRegistrosEntreDatas_e_Produto(datai,dataf,nomeProd);
                    binding.totalListaRg.setText(""+qtdRg);
                    double total=myDao.totalInvestidoEntreDatas_e_Produto(datai,dataf,nomeProd);
                    binding.totalLista.setText(""+formataValor(total));
                    binding.editDataComeco.setText(datai);
                    binding.editDataTerminio.setText(dataf);
                    binding.produtoEscolhido.setText(nomeProd);
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