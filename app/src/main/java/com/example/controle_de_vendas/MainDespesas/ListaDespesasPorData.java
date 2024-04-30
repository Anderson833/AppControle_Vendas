package com.example.controle_de_vendas.MainDespesas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.controle_de_vendas.DaoBd.MyDaoDespesa;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.Modelo.Despesas;
import com.example.controle_de_vendas.databinding.ActivityListaDespesasPorDataBinding;

import java.text.DecimalFormat;
import java.util.List;

public class ListaDespesasPorData extends AppCompatActivity {
  private ActivityListaDespesasPorDataBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityListaDespesasPorDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerviewDataDespesas.setLayoutManager(layoutManager);
        binding.recyclerviewDataDespesas.setHasFixedSize(true);
        String data=getIntent().getStringExtra("data");
        listaPelaData(data);
    }

    public void listaPelaData (String dat) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MyBancoControle_venda bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();
                    MyDaoDespesa myDaodesp = bd.myDaoDespesa();
                    List<Despesas> listDesp = myDaodesp.getAllDespesasPelaData(dat);
                    AdapterDespesa adapter = new AdapterDespesa(listDesp);
                    binding.recyclerviewDataDespesas.setAdapter(adapter);
                    String qtdRg=myDaodesp.qtdRegistrosDespesaData(dat);
                    binding.totalListaRg.setText(""+qtdRg);
                    double total=myDaodesp.totalDespesaPelaData(dat);
                    binding.totalLista.setText(""+formataValor(total));
                    binding.editDat.setText(dat);
                   // alteraNomeCampo=true;
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