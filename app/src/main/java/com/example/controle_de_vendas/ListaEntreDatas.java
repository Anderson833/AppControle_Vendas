package com.example.controle_de_vendas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.controle_de_vendas.DaoBd.MyDao;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.Modelo.Investimento;
import com.example.controle_de_vendas.databinding.ActivityListaEntreDatasBinding;

import java.text.DecimalFormat;
import java.util.List;

public class ListaEntreDatas extends AppCompatActivity {
  private ActivityListaEntreDatasBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityListaEntreDatasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerviewEntreDadas.setLayoutManager(layoutManager);
        binding.recyclerviewEntreDadas.setHasFixedSize(true);

        listaEntreDatas(getIntent().getStringExtra("datai"),getIntent().getStringExtra("dataf"));
    }

    public String formataValor(double valor){
        //  classe DecimaFormat para colocar os valores em casas decimais
        DecimalFormat decimalFormat  = new DecimalFormat("#,##0.00");
        String valorConvertido=decimalFormat.format(valor);
        return valorConvertido;
    }

    public void listaEntreDatas (String datai,String dataf) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyBancoControle_venda bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();
                MyDao myDao = bd.myDao();
                List<Investimento> listInvestir = myDao.listaTodosEntreDatas(datai,dataf);
                Adapter adapter = new Adapter(listInvestir);
                binding.recyclerviewEntreDadas.setAdapter(adapter);
                String qtdRg=myDao.qtdRegistrosEntreDatas(datai,dataf);
                binding.totalListaRg.setText(""+qtdRg);
                double total=myDao.totalInvestidoEntreDatas(datai,dataf);
                binding.totalLista.setText(""+formataValor(total));
                binding.datainicio.setText(datai);
                binding.datafinal.setText(dataf);

            }
        }).start();
    }
}