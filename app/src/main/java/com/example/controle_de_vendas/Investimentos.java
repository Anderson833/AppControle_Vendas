package com.example.controle_de_vendas;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.controle_de_vendas.DaoBd.MyDao;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.Modelo.Investimento;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Investimentos extends AppCompatActivity {
    private  static final String TAG="Produtos";
    private EditText nome_produto,quantidade_Prod,valor_a_Pagar, preco_revenda;
    private TextView data,totalInvest;
    private Button listaProduto, addInvestimento;
    private Calendar calendar;
    private MyBancoControle_venda bd;
     boolean opcao=true;
    private Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investimentos);
        nome_produto = findViewById(R.id.editNomeProduto_);
        addInvestimento = findViewById(R.id.botaoAdd);
        listaProduto = findViewById(R.id.botaolistaProd);
        quantidade_Prod=findViewById(R.id.editQuantidade);
        valor_a_Pagar = findViewById(R.id.editValorPagor);
        data =findViewById(R.id.dataInvestir);
        totalInvest =  findViewById(R.id.totalInvestido);
        preco_revenda =findViewById(R.id.editPrecoRevenda);
        calendar=Calendar.getInstance();
        setaData();
        updateInvestimentos();
   bd = Room.databaseBuilder(getApplication(),MyBancoControle_venda.class,"Meu_bd").build();
        addInvestimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //new CadastrarInvestimentos().start();
                if(verificaTodosCampos()){
                }else{

                    if(opcao==false){
                        new alterarInvestimentos().start();
                        Toast.makeText(Investimentos.this, "Dados atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    addInvestimento.setText("ADICIONAR");
                    }else{

                   new CadastrarInvestimentos().start();
                    Toast.makeText(Investimentos.this, "Dados adicionados com sucesso!", Toast.LENGTH_SHORT).show();
                  //  Log.i(TAG,"AÇÃO BOTAÕ ADD");
                    }
                    ///listagem();
                 //   test();
                }
            }
        });
        quantidade_Prod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //aqui executar tudo antes de ser tira o clique do campo
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // aqui na hora que está digitando no campo

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // aqui exibir depois do clique

                if(verificaCamposQtdValorRv()){
                }else{
                    setaCalculor();
                }
            }
        });
        preco_revenda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //aqui executar tudo antes de ser tira o clique do campo
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // aqui na hora que está digitando no campo
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // aqui exibir depois do clique

                if(verificaCamposQtdValorRv()){

                }else{
                    setaCalculor();
                }
            }
        });
    }
    public  void listaProdutos(View v){
        Intent intt = new Intent(this,MainActivity.class);
        startActivity(intt);
        finish();
    }
    public boolean verificaCamposQtdValorRv(){
        boolean verificar=true;
        String qtd=quantidade_Prod.getText().toString();
        String valorRv=preco_revenda.getText().toString();
        if(qtd.equals("")&&valorRv.equals("")){
            verificar=true;
        } else if (qtd.equals("")&&!valorRv.equals("")) {
            verificar=true;
        } else if (!qtd.equals("")&&valorRv.equals("")) {
            verificar=true;
        }else {
            verificar=false;
        }
        return verificar;
    }
    //  classe DecimaFormat para colocar os valores em casas decimais
    DecimalFormat decimalFormat  = new DecimalFormat("#,##0.00");
    public void setaCalculor(){
        String corrigir=quantidade_Prod.getText().toString();
        String correto=corrigir.replace("-","");
        int qtd=Integer.parseInt(correto);
        double valorRv=Double.parseDouble(preco_revenda.getText().toString());
        double totalCal=qtd*valorRv;
        String convertEmCasasDecimais=decimalFormat.format(totalCal);
        totalInvest.setText(""+convertEmCasasDecimais);
    }
    public  boolean verificaTodosCampos(){
        boolean preencheCampo=true;
        String nome,quantidadeProduto,valorRevenda,totalPagor;
        nome=nome_produto.getText().toString();
        quantidadeProduto=quantidade_Prod.getText().toString();
        valorRevenda=preco_revenda.getText().toString();
        totalPagor=valor_a_Pagar.getText().toString();

        if (nome.equals("")&&quantidadeProduto.equals("")&&valorRevenda.equals("")&&totalPagor.equals("")){
            Toast.makeText(this, "Preencha os campos!", Toast.LENGTH_SHORT).show();
            preencheCampo=true;
        } else if (nome.equals("")&&quantidadeProduto!=null&&valorRevenda!=null&&totalPagor!=null) {
            Toast.makeText(this, "Preencha o campo de nome!", Toast.LENGTH_SHORT).show();
            nome_produto.requestFocus();
            preencheCampo=true;
        } else if (nome!=null&&quantidadeProduto.equals("")&&valorRevenda!=null&&totalPagor!=null) {
            Toast.makeText(this, "Preencha o campo Quantidade!", Toast.LENGTH_SHORT).show();
            quantidade_Prod.requestFocus();
            preencheCampo=true;
        } else if (nome!=null&&quantidadeProduto!=null&&valorRevenda.equals("")&&totalPagor!=null) {
            Toast.makeText(this, "Preencha o campo Preço de revenda!", Toast.LENGTH_SHORT).show();
            preco_revenda.requestFocus();
            preencheCampo=true;
        } else if (nome!=null&&quantidadeProduto!=null&&valorRevenda!=null&&totalPagor.equals("")) {
            Toast.makeText(this, "Preencha o campo valor a pagar!", Toast.LENGTH_SHORT).show();
            preencheCampo=true;
            valor_a_Pagar.requestFocus();
        }else{
            preencheCampo=false;
        }
        return preencheCampo;
    }
    public void setaData(){
        Calendar dataEscolhida = Calendar.getInstance();
        date= dataEscolhida.getTime();
        SimpleDateFormat padra = new SimpleDateFormat("dd/MM/yyyy");
        data.setText(padra.format(date));

    }

    public void listagem(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyDao myDao=bd.myDao();
                for (Investimento i:myDao.getAllInvestimentos()) {
                    Log.i(TAG,"Id: "+i.getId());
                    Log.i(TAG,"Nome: "+i.getNomeProd());
                    Log.i(TAG,"data: "+i.getData());
                    Log.i(TAG,"Qtd: "+i.getQuantidade());
                    Log.i(TAG,"valor rv: "+i.getValorRv());
                    Log.i(TAG,"valor pag: "+i.getPrecoPg());
                    Log.i(TAG,"Total: "+i.getTodoTotal());
                }
            }
        }).start();
    }

    class CadastrarInvestimentos extends Thread{
        @Override
        public void run() {
            super.run();

            MyDao myDao =bd.myDao();
            Log.i(TAG,"Testando MÉTODO ADD");
            String nome=nome_produto.getText().toString();
            Log.i(TAG,"NOME: "+nome);
            int qtd=Integer.parseInt(quantidade_Prod.getText().toString());
            Log.i(TAG,"Qtd: "+qtd);
            double valorRevenda=Double.parseDouble(preco_revenda.getText().toString());
            Log.i(TAG,"Valor Rv: "+valorRevenda);
            double valorPagor=Double.parseDouble(valor_a_Pagar.getText().toString());
            Log.i(TAG,"valor pag: "+valorPagor);
            // aqui pra baixo não add
            double valorInvestido=qtd*valorRevenda;
            Log.i(TAG,"total : "+valorInvestido);
            String dat=data.getText().toString();
            Log.i(TAG,"data: "+dat);
            myDao.insertInvetimentos(new Investimento(0,nome,dat,qtd,valorRevenda,valorPagor,valorInvestido));

            nome_produto.setText("");
            quantidade_Prod.setText("");
            preco_revenda.setText("");
            valor_a_Pagar.setText("");
            totalInvest.setText("");

        }
    }

    class alterarInvestimentos extends Thread{
        @Override
        public void run() {
            super.run();

            MyDao myDao =bd.myDao();
            Log.i(TAG,"Testando MÉTODO ADD");
            int id = getIntent().getIntExtra("id", 0);
            String nome=nome_produto.getText().toString();
            Log.i(TAG,"NOME: "+nome);
            int qtd=Integer.parseInt(quantidade_Prod.getText().toString());
            Log.i(TAG,"Qtd: "+qtd);
            double valorRevenda=Double.parseDouble(preco_revenda.getText().toString());
            Log.i(TAG,"Valor Rv: "+valorRevenda);
            double valorPagor=Double.parseDouble(valor_a_Pagar.getText().toString());
            Log.i(TAG,"valor pag: "+valorPagor);
            // aqui pra baixo não add
            double valorInvestido=qtd*valorRevenda;
            Log.i(TAG,"total : "+valorInvestido);
            String dat=data.getText().toString();
            Log.i(TAG,"data: "+dat);
            myDao.alteraDados(new Investimento(id,nome,dat,qtd,valorRevenda,valorPagor,valorInvestido));

            nome_produto.setText("");
            quantidade_Prod.setText("");
            preco_revenda.setText("");
            valor_a_Pagar.setText("");
            totalInvest.setText("");

        }
    }


    public  void updateInvestimentos() {

        int id = getIntent().getIntExtra("id", 0);
        int quantidade = getIntent().getIntExtra("qtd", 0);
        String nome = getIntent().getStringExtra("nome");
        String data = getIntent().getStringExtra("data");
        double precoRv = getIntent().getDoubleExtra("precoRv", 0);
        double valorPg = getIntent().getDoubleExtra("valorPg", 0);
        double total = getIntent().getDoubleExtra("total", 0);
        Log.i("update", "opcao"+getIntent().getStringExtra("opcao"));
        Log.i("update", " id de integer " + id);
        Log.i("update", "nom " + nome);
        Log.i("update", "data " + data);
        Log.i("update", "qtd " + quantidade);
        Log.i("update", "valor rv " + precoRv);
        Log.i("update", "valor pg " + valorPg);
        Log.i("update", "total " + total);

        if (id == 0) {
            Log.i("update", "Entrou no if ");
        } else {
            nome_produto.setText(nome);
            quantidade_Prod.setText(String.valueOf(quantidade));
            valor_a_Pagar.setText(String.valueOf(valorPg));
            preco_revenda.setText(String.valueOf(valorPg));
            totalInvest.setText(String.valueOf(total));
            addInvestimento.setText("ALTERAR");
            opcao=false;
            //  new AlteraDados().start();
           // updateDados();
            Log.i("update","entrou no else");
          //  lista();
        }
    }

}