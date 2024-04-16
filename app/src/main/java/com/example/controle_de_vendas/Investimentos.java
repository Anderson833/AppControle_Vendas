package com.example.controle_de_vendas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.controle_de_vendas.DaoBd.MyDao;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.Modelo.Investimento;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *  Essa classe servi para executar todas funcionalidades do layout Investimentos
 */
public class Investimentos extends AppCompatActivity {
    // Variáveis do tipo EditText para pegar os dados dos campos do layout
    private EditText nome_produto,quantidade_Prod,valor_a_Pagar, preco_revenda;
    // Variáveis do tipo TexText para pegar os dados dos campos do layout ou exibir
    private TextView data,totalInvest;
    // Variável dat para armazenar a daata formatada
    private String dat="";
    // Variável do tipo Button para realizar o cadastro e a listagem dos dados
    private Button listaProduto, addInvestimento;
    private Calendar calendar;
    //Variável banco de dados na linha de baixo
    private MyBancoControle_venda bd;
    // Variável opcao do tipo lógico para armazenar uma valor verdadeiro ou falso
         boolean opcao=true;
         private  double valorRv=0.0;
    String vl="",valorPag="";
    //Variável do tipo date para manipular a data
    private Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investimentos);
        // Passando os dados para as variáveis dos campos conforme seus campos
        nome_produto = findViewById(R.id.editNomeProduto_);
        addInvestimento = findViewById(R.id.botaoAdd);
        listaProduto = findViewById(R.id.botaolistaProd);
        quantidade_Prod = findViewById(R.id.editQuantidade);
        valor_a_Pagar = findViewById(R.id.editValorPagor);
        data = findViewById(R.id.dataInvestir);
        totalInvest = findViewById(R.id.totalInvestido);
        preco_revenda = findViewById(R.id.editPrecoRevenda);
        calendar = Calendar.getInstance();
        // Método exibir a data atual do dia no campo de data
        setaData();
        // Método para exibir os dados que vem do layout lista de produtos para alterar
        setaDadosInvestimentos();
        // Instânciando um objeto do banco de dados
        bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();

/**
 *  Método para realizar uma ação no campo de data
 */
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // As variavésis com os números do ano mês e dia
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                // método instânciado na linha de baixo para executar a exibição do calendário
                DatePickerDialog datePickerDialog = new DatePickerDialog(Investimentos.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                        // o month começa com indice 0.
                        // O Ano tem 12 meses para aparecer 0s números correto de cada mês acresento mais 1 para cada mês
                        month++;
                        // condição para acresenta o número 0 se o dia e mês tiver um número menor que dez
                        if (dayofMonth > 0 && dayofMonth < 10 && month > 0 && month < 10) {
                            // Imprimir no campo de texto a data nesse formato que está nalinha de baixo
                            dat = "0" + dayofMonth + "/0" + month + "/" + year;
                            //Condição para saber se o dia é menor do que deez e o mês maior do que nove e menor do que 13
                        } else if (dayofMonth > 0 && dayofMonth < 10 && month > 9 && month < 13) {
                            // caso seja executada essa condição vai imprimir a data nesse formator que está na linha de baixo no campo de texto
                            dat = "0" + dayofMonth + "/" + month + "/" + year;
                            //condição para saber se caso o dia seja maior do que nove e menor do que trinta e um.
                            // E mês maior do que  0 e menor do que 10
                        } else if (dayofMonth > 9 && dayofMonth <= 31 && month > 0 && month < 10) {
                            //caso seja executado essa condição, imprimir a data no formator que está na linha de baixo no campo de texto
                            dat = dayofMonth + "/0" + month + "/" + year;
                        } else {
                            // se caso não executar nenhuma das condições anterior, será imprimido a data no formator que está na linha de baixo
                            dat = dayofMonth + "/" + month + "/" + year;
                        }
                        // O campo de texto vai exibir a data conforme a condição executada.
                        data.setText(dat);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });
        /**
         * Método para realizar uma ação no botão de adicionar
         */
        addInvestimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Condição para saber se os campos estão vazios
                if (verificaTodosCampos()) {
                    /// É excutador o que está no método verificarTodosCampos
                } else {
                    //condição para saber se a opção é false
                    if (opcao == false) {
                        // Método para alterar os dados do investimentos
                        new alterarInvestimentos().start();
                        // Informando uma mensagem para o usuário na linha de baixo
                        Toast.makeText(Investimentos.this, "Dados atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                        // Imprimindo o nome adicionar no butão
                        addInvestimento.setText("ADICIONAR");
                    } else {
                        // Método para cadastrar os investimentos
                        new CadastrarInvestimentos().start();
                        // informa uma mensagem para usuário na linha de baixo
                        Toast.makeText(Investimentos.this, "Dados adicionados com sucesso!", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        /**
         * Método para executar no campo de quantidade, assim que for digitado algum dado
         */
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
                try {
                    if (verificaCamposQtdValorRv()) {
                        // caso esteja vai se executado o método verificaCamposQtdValorRv
                    } else {
                        // Método será excutado para calcular a quantidade de produtos vezes o preço de revenda
                        setaCalculor();
                    }
                }catch (Exception e){
                    Log.i("iff","error "+e);
                }
                    // Condição para saber se os campos de quantidade e valor revenda estão vazios

                }
        });
        /**
         * Método para excutar uma ação no campo de preço de revenda
         */

        preco_revenda.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                // Condição para saber se os campos de quantidade e valor revenda estão vazios
                if (verificaCamposQtdValorRv()) {
                    // caso esteja vai se executado o método verificaCamposQtdValorRv
                } else {
                    // Método será excutado para calcular a quantidade de produtos vezes o preço de revenda
                    setaCalculor();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().indexOf(".")==0){
                    preco_revenda.setText("0");
                }else {

                    if (!s.toString().equals(vl)) {
                        preco_revenda.removeTextChangedListener(this);
                        String precoString = s.toString().replaceAll("[R$.]", "");
                        precoString = precoString.replaceAll(",", ".");
                        Double precoDouble = 0.0;
                        if (s.length() > vl.length()) {
                            precoDouble = Double.valueOf(precoString);
                            precoDouble = precoDouble * 10;
                        }
                        if (s.length() < vl.length()) {
                            precoDouble = Double.valueOf(precoString);
                            precoDouble = precoDouble / 10;
                        }
                        precoString = precoDouble.toString();
                        NumberFormat formatoReal = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                        String formatado = formatoReal.format(Double.valueOf(precoString));
                        preco_revenda.setText(formatado);
                        vl = formatado;
                        preco_revenda.setText(formatado);
                        preco_revenda.setSelection(formatado.length());
                        preco_revenda.addTextChangedListener(this);

                    }
                }
            }
        });



    }

    public String formataValor(double valor){
        //  classe DecimaFormat para colocar os valores em casas decimais
        DecimalFormat decimalFormat  = new DecimalFormat("#,##0.00");
            String valorConvertido=decimalFormat.format(valor);
       return valorConvertido;
    }
    //  classe DecimaFormat para colocar os valores em casas decimais
    DecimalFormat decimalFormat  = new DecimalFormat("#,##0.00");
    /**
     * Método para lista os dados salvos no banco de dados ao abrir a tela de lista de produtos
     * @param v
     */
    public  void listaProdutos(View v){
        Intent intt = new Intent(this,MainActivity.class);
        startActivity(intt);
      //  finish();
    }

    /**
     * Método para verificar se os campos de quantidade e preço revenda estão vazios
     * @return um dado logico
     */
    public boolean verificaCamposQtdValorRv(){
        boolean verificar=true;
        String qtd=quantidade_Prod.getText().toString();
        String valorRv=preco_revenda.getText().toString();
        if (!qtd.equals("")&&valorRv.equals("")) {
            verificar=true;
        } else if (qtd.equals("")&&!valorRv.equals("")) {
            verificar=true;
        }else {
            verificar=false;
        }
        if(qtd.equals("")&&valorRv.equals("")){
            totalInvest.setText("");
        }
        if(qtd.equals("")||valorRv.equals("")){
            totalInvest.setText("");
        }
        return verificar;
    }


    /**
     * Método para executar uma multiplicação de quantidade de produtos com o valor e revenda
     */
    public void setaCalculor(){
        String corrigir=quantidade_Prod.getText().toString();
        String correto=corrigir.replace("-","");
        int qtd=Integer.parseInt(correto);
         valorRv=Double.parseDouble(convertParaDouble(preco_revenda.getText().toString()));
        Log.i("gg","vl rv"+valorRv);
        double totalCal=qtd*valorRv;
        String convertEmCasasDecimais=decimalFormat.format(totalCal);
        totalInvest.setText(""+convertEmCasasDecimais);
    }

    public String convertParaDouble(String dado){
        String precoDouble =dado.replaceAll("[R$.]", "");
        precoDouble = precoDouble.replaceAll(",", ".");
        NumberFormat formatoDouble = NumberFormat.getNumberInstance(new Locale("en", "US"));
        String formatado = formatoDouble.format(Double.valueOf(precoDouble)).replaceAll(",", "");

        return formatado;
    }

    /**
     * Método para verificar se os campos estão vazios
     * @return um valor logico
     */
    public  boolean verificaTodosCampos(){
        // variável do tipo logico com nome preencheCampo recebendo valor verdadeiro
        boolean preencheCampo=true;
        // Criados as variáveis do tipo de dado String para armazenar os dados dos campos;
        String nome,quantidadeProduto,valorRevenda,totalPagor;
        // Passando os dados dos campos para as variáveis que foram criadas acima anteriomente
        nome=nome_produto.getText().toString();
        quantidadeProduto=quantidade_Prod.getText().toString();
        valorRevenda=preco_revenda.getText().toString();
        totalPagor=valor_a_Pagar.getText().toString();
//       criando as condições para cada verifica todos campos e para cada campo especificos
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
        // retornando uma valor lógico conforme a condição que for executada
        return preencheCampo;
    }

    /**
     * Método para exibir a data atual do dia no campo de data
     */
    public void setaData(){
        Calendar dataEscolhida = Calendar.getInstance();
        date= dataEscolhida.getTime();
        SimpleDateFormat padra = new SimpleDateFormat("dd/MM/yyyy");
        data.setText(padra.format(date));
    }

    /**
     * Método para cadastrar os dados dos investimentos
     */
    class CadastrarInvestimentos extends Thread{
        @Override
        public void run() {
            super.run();
            // criando um obj do banco de dados
            MyDao myDao =bd.myDao();
            Log.i("gg","Dados  estaveis ");
            // convertando os dados dos campos, para cada tipo de dados especificos nas linhas de baixo conforme o  esperado
            String nome=nome_produto.getText().toString();
            Log.i("gg","name  "+nome);
            int qtd=Integer.parseInt(quantidade_Prod.getText().toString());
            Log.i("gg","quant  "+qtd);
            double valorRevenda=valorRv;
            Log.i("gg","din revend "+valorRevenda);
            double valorPagor=Double.parseDouble(convertParaDouble(valor_a_Pagar.getText().toString()));
            Log.i("gg","din valor pagoo "+valorPagor);
            double valorInvestido=qtd*valorRevenda;
            Log.i("gg","total invest "+valorInvestido);
            String dat=data.getText().toString();
            Log.i("gg","data  "+dat);
         // Passando uma obj do banco de dados e criando um objeto do tio investimento
            myDao.insertInvetimentos(new Investimento(0,nome,dat,qtd,valorRevenda,valorPagor,valorInvestido));
            //Limpando todos os campos assim que inserir os dados no banco de dados
                try{
                    nome_produto.setText("");
                    quantidade_Prod.setText("");
                    preco_revenda.setText("");
                    valor_a_Pagar.setText("");
                    totalInvest.setText("");
                    valorPag="";
                }catch (Exception e){
                    Log.i("er",""+e);
                }
        }
    }

    /**
     * Método para alterar os dados do investimentos no banco de dados
     */
    class alterarInvestimentos extends Thread{
        @Override
        public void run() {
            super.run();
         ///Criando um objeto do tipo myDao do banco de dados
            MyDao myDao =bd.myDao();
            // convertando os dados dos campos, para cada tipo de dados especificos nas linhas de baixo conforme o  esperado
            int id = getIntent().getIntExtra("id", 0);
            String nome=nome_produto.getText().toString();
            int qtd=Integer.parseInt(quantidade_Prod.getText().toString());
            double valorRevenda=0;
            if(valorRv==0.0){
                valorRevenda=Double.parseDouble(convertParaDouble(preco_revenda.getText().toString()));
            }else{
                valorRevenda=valorRv;
            }
            double valorPagor=Double.parseDouble(convertParaDouble(valor_a_Pagar.getText().toString()));
            double valorInvestido=qtd*valorRevenda;
            String dat=data.getText().toString();
            // Passando uma obj do banco de dados e criando um objeto do  investimento
            myDao.alteraDados(new Investimento(id,nome,dat,qtd,valorRevenda,valorPagor,valorInvestido));
            // Limpando todos campos assim que alterar os dados do investimento no banco de dados
            try{
                nome_produto.setText("");
                quantidade_Prod.setText("");
                preco_revenda.setText("");
                totalInvest.setText("");
                valor_a_Pagar.setText("");
                valorPag="";
            }catch (Exception e){
                Log.i("er",""+e);
            }

        }
    }

    /**
     * Método para exibir os dados que vem do layout lista de produtos para imprimir nos seus campos especificos
     */
    public  void setaDadosInvestimentos() {
         //Pegando os dados da lista de produtos e convertendo em seus especificos dados
        int id = getIntent().getIntExtra("id", 0);
        int quantidade = getIntent().getIntExtra("qtd", 0);
        String nome = getIntent().getStringExtra("nome");
        String data = getIntent().getStringExtra("data");
        double precoRv = getIntent().getDoubleExtra("precoRv", 0);
        double valorPg = getIntent().getDoubleExtra("valorPg", 0);
        double total = getIntent().getDoubleExtra("total", 0);
        // Condição para saber se o id e igual a zero
        if(id==0){
        }else{
            // Caso o id não for igual a zero, será setados os dados nos seus especificos campos já convertidos
            nome_produto.setText(nome);
            quantidade_Prod.setText(String.valueOf(quantidade));
            valor_a_Pagar.setText(formataValor(valorPg));
            preco_revenda.setText(formataValor(precoRv));
            totalInvest.setText(formataValor(total));
            // Nomeando o botão de adicionar par alterar
            addInvestimento.setText("ALTERAR");
            // Passando um valor falso para variável opcao
            opcao=false;
        }
    }
}