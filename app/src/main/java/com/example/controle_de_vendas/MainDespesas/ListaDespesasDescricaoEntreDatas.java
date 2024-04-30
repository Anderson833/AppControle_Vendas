package com.example.controle_de_vendas.MainDespesas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.controle_de_vendas.DaoBd.MyDaoDespesa;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.Modelo.Despesas;
import com.example.controle_de_vendas.databinding.ActivityListaDespesasDescricaoEntreDatasBinding;

import java.text.DecimalFormat;
import java.util.List;

public class ListaDespesasDescricaoEntreDatas extends AppCompatActivity {

    private ActivityListaDespesasDescricaoEntreDatasBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityListaDespesasDescricaoEntreDatasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.ListagemDescricaoEntreDatas.setLayoutManager(layoutManager);
        binding.ListagemDescricaoEntreDatas.setHasFixedSize(true);
      String datainicio=getIntent().getStringExtra("datacomeco");
      String datafinal=getIntent().getStringExtra("datatermino");
      String descricao=getIntent().getStringExtra("descricao");
      listaDescricaoEntreDatas(datainicio,datafinal,descricao);
    }

    public void listaDescricaoEntreDatas (String datai,String dataf,String descricao) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MyBancoControle_venda bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();
                    MyDaoDespesa myDaodesp = bd.myDaoDespesa();
                    List<Despesas> listadespesas = myDaodesp.listaTodosDespesasEntreDatas(datai,dataf,descricao);
                    AdapterDespesa adapter = new AdapterDespesa(listadespesas);
                    binding.ListagemDescricaoEntreDatas.setAdapter(adapter);
                    String qtdRg=myDaodesp.qtdRegistrosDespesasEntreDatas(datai,dataf,descricao);
                    binding.totalListaRg.setText(""+qtdRg);
                    double total=myDaodesp.totalDescricaoEntreDatas(datai,dataf,descricao);
                    binding.totalLista.setText(""+formataValor(total));
                    binding.editDataComecoDesp.setText(datai);
                    binding.editDataTerminioDesp.setText(dataf);
                    binding.descricaoEscolhida.setText(descricao);
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