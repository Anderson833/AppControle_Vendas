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

public class ListaPorData extends AppCompatActivity {
    private TextView totalLista,totalListaRg, editDataEspecifica, mudaNomeTexto;
    private RecyclerView recyclerView;
    private boolean alteraNomeCampo=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_por_data);
        recyclerView = findViewById(R.id.recyclerviewData);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mudaNomeTexto = findViewById(R.id.mudaOpcao);
        editDataEspecifica = findViewById(R.id.editDataEspecifica);
        totalListaRg = findViewById(R.id.totalListaRg);
        totalLista = findViewById(R.id.totalLista);
        listaPelaData(getIntent().getStringExtra("data"));
    }
    public String formataValor(double valor){
        //  classe DecimaFormat para colocar os valores em casas decimais
        DecimalFormat decimalFormat  = new DecimalFormat("#,##0.00");
        String valorConvertido=decimalFormat.format(valor);
        return valorConvertido;
    }
    public void listaPelaData (String dat) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MyBancoControle_venda bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();
                    MyDaoInvestimento myDao = bd.myDao();
                    List<Investimento> listInvestir = myDao.getAllInvestimentosPelaData(dat);
                    Adapter adapter = new Adapter(listInvestir);
                    recyclerView.setAdapter(adapter);
                    String qtdRg=myDao.qtdRegistrosPelaData(dat);
                    totalListaRg.setText(""+qtdRg);
                    double total=myDao.totalInvestidoPelaData(dat);
                    totalLista.setText(""+formataValor(total));
                    editDataEspecifica.setText(dat);
                    alteraNomeCampo=true;
                }catch (Exception e){
                }
            }
        }).start();
    }
}