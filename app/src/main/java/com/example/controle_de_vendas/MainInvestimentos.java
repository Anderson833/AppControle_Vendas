package com.example.controle_de_vendas;

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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainInvestimentos extends AppCompatActivity {

    private  static final String TAG="Produtos";
     private EditText nome_produto,quantidade_Prod,valor_a_Pagar, preco_revenda;
     private TextView data,totalInvest;
     private Button listaProduto, addInvestimento;
     private Calendar calendar;
      //private MyBancoDados BANCO_Dados;
     private Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maininvestimentos);
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
       // BANCO_Dados = Room.databaseBuilder(getApplication(), MyBancoDados.class, "MEU_BD").build();
     Log.i(TAG,"onCreate: Inicio");
         addInvestimento.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 //new CadastrarInvestimentos().start();
                 if(verificaTodosCampos()){
                 }else{
                new CadastrarInvestimentos().start();
                 Log.i(TAG,"AÇÃO BOTAÕ ADD");
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

    class CadastrarInvestimentos extends Thread{
        @Override
        public void run() {
            super.run();
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
            double valorInvestido=Double.parseDouble(totalInvest.getText().toString());
            Log.i(TAG,"total : "+valorInvestido);
            String dat=data.getText().toString();
            Log.i(TAG,"data: "+dat);
         //   MyBanco myBanco = BANCO_Dados.myBanco();
            Log.i(TAG,"EM CIMA DA LINHA DO INSERT AO BD");
        //    myBanco.insertInvestimeto(new Investimento(0,nome,qtd,valorRevenda,valorPagor,dat,valorInvestido));
       //     Investimento in = new Investimento();
         //  Log.i(TAG,"valores"+in.getId());
           nome_produto.setText("");
            quantidade_Prod.setText("");
            preco_revenda.setText("");
            valor_a_Pagar.setText("");
            totalInvest.setText("");

        }
    }

}