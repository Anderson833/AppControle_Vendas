package com.example.controle_de_vendas.MainDespesas;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.controle_de_vendas.DaoBd.MyDaoDespesa;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.Modelo.Despesas;
import com.example.controle_de_vendas.databinding.ActivityDespesasBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DespesasMain extends AppCompatActivity {
  private ActivityDespesasBinding binding;
    Calendar calendar;
  private  String dat="";
    private Date date;
    private MyBancoControle_venda bd;
    private  boolean opcao=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDespesasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        calendar = Calendar.getInstance();
        setaData();
        setaDadosDespesas();
        // Instânciando um objeto do banco de dados
        bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();
        binding.editDataDesp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setaCalendario(binding.editDataDesp);
            }
        });
        /**
         * Método para realizar uma ação no botão de adicionar
         */
        binding.botaoAddDespesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Condição para saber se os campos estão vazios
                if (verificaTodosCampos()) {
                    /// É excutador o que está no método verificarTodosCampos
                } else {
                    //condição para saber se a opção é false
                    if (opcao == false) {
                        // Método para alterar os dados de despesa
                        new alterarDespesas().start();
                        // Informando uma mensagem para o usuário na linha de baixo
                        Toast.makeText(DespesasMain.this, "Dados atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                        // Imprimindo o nome adicionar no butão
                        binding.botaoAddDespesas.setText("ADICIONAR");
                        binding.textTitulo.setText("Cadastrar");
                        binding.editTotalDespesa.setText("");
                    } else {
                        // Método para cadastrar os investimentos
                        new CadastrarDespesa().start();
                        binding.editTotalDespesa.setText("");
                        // informa uma mensagem para usuário na linha de baixo
                        Toast.makeText(DespesasMain.this, "Dados adicionados com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    /**
     * Método para exibir a data atual do dia no campo de data
     */
    public void setaData(){
        Calendar dataEscolhida = Calendar.getInstance();
        date= dataEscolhida.getTime();
        SimpleDateFormat padra = new SimpleDateFormat("dd/MM/yyyy");
        binding.editDataDesp.setText(padra.format(date));
    }
    public void setaCalendario(TextView t){
        // As variavésis com os números do ano mês e dia
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        // método instânciado na linha de baixo para executar a exibição do calendário
        DatePickerDialog datePickerDialog = new DatePickerDialog(DespesasMain.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                // o month começa com indice 0.
                // O Ano tem 12 meses para aparecer 0s números correto de cada mês acresento mais 1 para cada mês
                month++;
                // condição para acresenta o número 0 se o dia e mês tiver um número menor que dez
                if(dayofMonth>0 && dayofMonth<10 && month>0 && month<10){
                    // Imprimir no campo de texto a data nesse formato que está nalinha de baixo
                    dat="0"+dayofMonth+"/0"+month+"/"+year;
                    //Condição para saber se o dia é menor do que deez e o mês maior do que nove e menor do que 13
                } else if(dayofMonth>0 && dayofMonth<10 && month>9 && month<13){
                    // caso seja executada essa condição vai imprimir a data nesse formator que está na linha de baixo no campo de texto
                    dat="0"+dayofMonth+"/"+month+"/"+year;
                    //condição para saber se caso o dia seja maior do que nove e menor do que trinta e um.
                    // E mês maior do que  0 e menor do que 10
                }else if(dayofMonth>9 && dayofMonth<=31 && month>0 && month<10){
                    //caso seja executado essa condição, imprimir a data no formator que está na linha de baixo no campo de texto
                    dat=dayofMonth+"/0"+month+"/"+year;
                }else{
                    // se caso não executar nenhuma das condições anterior, será imprimido a data no formator que está na linha de baixo
                    dat=dayofMonth+"/"+month+"/"+year;
                }
                // O campo de texto vai exibir a data conforme  a condição executada.
                t.setText(dat);
            }
        },year,month,day);datePickerDialog.show();

    }
    public  boolean verificaTodosCampos(){
        // variável do tipo logico com nome preencheCampo recebendo valor verdadeiro
        boolean preencheCampo=true;
      String nomeCliente=binding.editDescricao.getText().toString();
      String data=binding.editDataDesp.getText().toString();
      String total=binding.editTotalDespesa.getText().toString();
//       criando as condições para cada verifica todos campos e para cada campo especificos
        if (nomeCliente.equals("")&&data.equals("")&&total.equals("")){
            Toast.makeText(this, "Preencha os campos!", Toast.LENGTH_SHORT).show();
            preencheCampo=true;
        } else if (nomeCliente.equals("")&&data!=null&&total!=null) {
            Toast.makeText(this, "Preencha o campo de nome !", Toast.LENGTH_SHORT).show();
            binding.editDescricao.requestFocus();
            preencheCampo=true;
        } else if (nomeCliente!=null&&data.equals("")&&total!=null) {
            Toast.makeText(this, "Preencha o campo de data!", Toast.LENGTH_SHORT).show();
            preencheCampo=true;
        } else if (nomeCliente!=null&&data!=null&&total.equals("")) {
            Toast.makeText(this, "Preencha o campo do total!", Toast.LENGTH_SHORT).show();
            binding.editTotalDespesa.requestFocus();
            preencheCampo=true;
        }else{
            preencheCampo=false;
        }
        // retornando uma valor lógico conforme a condição que for executada
        return preencheCampo;
    }
    /**
     * Método para cadastrar os dados de despesa
     */
    class CadastrarDespesa extends Thread{
        @Override
        public void run() {
            super.run();
            // criando um obj do banco de dados
            MyDaoDespesa myDesp = bd.myDaoDespesa();
           String DESCRICAO =binding.editDescricao.getText().toString();
            double total=Double.parseDouble(binding.editTotalDespesa.getText().toString());
            // Passando uma obj do banco de dados e criando um objeto do tipo despesas
            String dt=binding.editDataDesp.getText().toString();
        myDesp.insertDespesas(new Despesas(0,DESCRICAO,dt,total));
            //Limpando todos os campos assim que inserir os dados no banco de dados
           try{
                binding.editDescricao.setText("");
                binding.editTotalDespesa.setText("");
            }catch (Exception e){
            }
        }
    }

    /**
     * Método para alterar os dados de despesas no banco de dados
     */
    class alterarDespesas extends Thread{
        @Override
        public void run() {
            super.run();
            ///Criando um objeto do tipo myDao do banco de dados
            MyDaoDespesa myDaoDesp =bd.myDaoDespesa();
            // convertando os dados dos campos, para cada tipo de dados especificos nas linhas de baixo conforme o  esperado
            int id = getIntent().getIntExtra("id", 0);
            String descricao=binding.editDescricao.getText().toString();
            String data=binding.editDataDesp.getText().toString();
            double total=Double.parseDouble(binding.editTotalDespesa.getText().toString());
            // Passando uma obj do banco de dados e criando um objeto do  investimento
            myDaoDesp.alteraDadosDespesa(new Despesas(id,descricao,data,total));
            // Limpando todos campos assim que alterar os dados do investimento no banco de dados
            try{
                binding.editDescricao.setText("");
                binding.editDataDesp.setText("");
                binding.editTotalDespesa.setText("");
            }catch (Exception e){
            }
        }
    }

    /**
     * Método para exibir os dados que vem do layout de lista de despesas para imprimir nos seus campos especificos
     */
    public  void setaDadosDespesas() {
        //Pegando os dados da lista de produtos e convertendo em seus especificos dados
        int id = getIntent().getIntExtra("id", 0);
        String descricao = getIntent().getStringExtra("descricao");
        String data = getIntent().getStringExtra("data");
        double total=getIntent().getDoubleExtra("total",0);
        // Condição para saber se o id e igual a zero
        if(id==0){
        }else{
            // Caso o id não for igual a zero, será setados os dados nos seus especificos campos já convertidos
            binding.editDescricao.setText(descricao);
            binding.editDataDesp.setText(data);
            binding.editTotalDespesa.setText(""+total);
            // Nomeando o botão de adicionar par alterar
            binding.botaoAddDespesas.setText("ALTERAR");
            binding.textTitulo.setText("Atualizar");
            // Passando um valor falso para variável opcao
            opcao=false;
        }
    }
}