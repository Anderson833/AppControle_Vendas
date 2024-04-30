package com.example.controle_de_vendas.MainDespesas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.controle_de_vendas.DaoBd.MyDaoDespesa;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.Modelo.Despesas;
import com.example.controle_de_vendas.databinding.ActivityListaDespesasBinding;

import java.text.DecimalFormat;
import java.util.List;

public class ListaDespesas extends AppCompatActivity {
 private ActivityListaDespesasBinding binding;
    private MyBancoControle_venda bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityListaDespesasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.RecycleviewListaDespesas.setLayoutManager(layoutManager);
        binding.RecycleviewListaDespesas.setHasFixedSize(true);
        listaDespesas();
    }

    public void listaDespesas () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();
                    MyDaoDespesa myDaoDespesa =bd.myDaoDespesa();
                    List<Despesas> listaDespesas =myDaoDespesa.getAllDespesas();
                    AdapterDespesa adapterD = new AdapterDespesa(listaDespesas);
                    binding.totalListaRg.setText(""+myDaoDespesa.qtdRegistrosDespesas());
                    binding.totalLista.setText(""+formataValor(myDaoDespesa.todoTotalDespesas()));
                    binding.RecycleviewListaDespesas.setAdapter(adapterD);
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