package com.example.controle_de_vendas;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.room.Room;

import com.example.controle_de_vendas.DaoBd.MyDao;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.databinding.ActivityTelaPrincipalBinding;

import java.util.Calendar;
import java.util.List;

public class TelaPrincipal extends AppCompatActivity {
  private ActivityTelaPrincipalBinding binding;
  private  String dat="";
    Calendar calendar;
    private MyBancoControle_venda bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        calendar=Calendar.getInstance();
        PopupMenu popupMenu = new PopupMenu(TelaPrincipal.this,binding.listaTodos);
        popupMenu.getMenu().add(Menu.NONE,0,0,"Cadastrar");
        popupMenu.getMenu().add(Menu.NONE,1,1,"Lista Todos Dados");
        popupMenu.getMenu().add(Menu.NONE,2,2,"Lista Por Data");
        popupMenu.getMenu().add(Menu.NONE,3,3,"Lista Por Produto");
        popupMenu.getMenu().add(Menu.NONE,4,4,"Lista Por Data e Produto");
        popupMenu.getMenu().add(Menu.NONE,5,5,"Lista Entre Datas");
        popupMenu.getMenu().add(Menu.NONE,6,6,"Lista Produto Entre Datas");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id =menuItem.getItemId();
                if(id==0){
                    abrirTelaCadastro();
                }else if(id==1){
                    listaTodosInvestimentos();
                }else if(id==2){
                    exibirLayoutPorData();
                }else if(id==3){
                    exibirLayoutPorProduto();
                }else if(id==4){
                    exibirLayoutData_E_Produto();
                }else if(id==5){
                    exibirLayoutEntreDatas();
                }else if(id==6){
                    exibirLayoutProduto_Entre_Datas();
                }
                return false;
            }
        });

        binding.listaTodosDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             popupMenu.show();
            }
        });
    }

    public  void listaTodosInvestimentos(){
        Intent abrirTelaMain = new Intent(TelaPrincipal.this,MainActivity.class);
        startActivity(abrirTelaMain);
    }
    public  void abrirTelaCadastro(){
        Intent abrirTelaMain = new Intent(TelaPrincipal.this,Investimentos.class);
        startActivity(abrirTelaMain);
    }
    public void exibirLayoutPorData(){
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.layoutpordata);
        TextView campoData =d.findViewById(R.id.editDataEspecificar);
        AppCompatButton bt =d.findViewById(R.id.botaopData);
        campoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setaCalendario(campoData);
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(campoData.getText().toString().equals("")) {
                    Toast.makeText(TelaPrincipal.this, "Preencha o campo Data!", Toast.LENGTH_SHORT).show();
                }else{
                    listaPelaData(campoData.getText().toString());
                    d.dismiss();
                }
            }
        });d.show();
    }
    public void exibirLayoutData_E_Produto(){
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.layoutprodutoedata);
        TextView campoData =d.findViewById(R.id.editDataEspecificaProd);
        Spinner spinner = d.findViewById(R.id.escolheProdutoOpcao);
        AppCompatButton btDataProduto =d.findViewById(R.id.botaoEntreDataProduto);
        listaNomeProdutos(spinner);
        campoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setaCalendario(campoData);
            }
        });
        btDataProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(campoData.getText().toString().equals("")) {
                    Toast.makeText(TelaPrincipal.this, "Preencha o campo Data!", Toast.LENGTH_SHORT).show();
                }else if(spinner.getSelectedItem().equals("")){
                    Toast.makeText(TelaPrincipal.this, "Selecioner um Produto!", Toast.LENGTH_SHORT).show();
                }else{
                    String nomeProduto=String.valueOf(spinner.getSelectedItem());
                    listaPeloNomeProduto_Data(campoData.getText().toString(),nomeProduto);
                    d.dismiss();
                }
            }
        });d.show();
    }
    public void exibirLayoutProduto_Entre_Datas() {
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.layoutprodutoentredatas);
        TextView campoDataInicio = d.findViewById(R.id.editDataComeco);
        TextView campoDataTermino = d.findViewById(R.id.editDataTerminio);
       Spinner spinner = d.findViewById(R.id.escolheAlgumProduto);
       listaNomeProdutos(spinner);
        AppCompatButton btDataProdutos = d.findViewById(R.id.botaoProdutoEntreDatas);
        campoDataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setaCalendario(campoDataInicio);
            }
        });
        campoDataTermino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setaCalendario(campoDataTermino);
            }
        });
        btDataProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                String datai=campoDataInicio.getText().toString();
                String dataf=campoDataTermino.getText().toString();
                if (datai.equals("")) {
                    Toast.makeText(TelaPrincipal.this, "Preencha o campo data início!", Toast.LENGTH_SHORT).show();
                }else if(dataf.equals("")) {
                    Toast.makeText(TelaPrincipal.this, "Preencha o campo data final!", Toast.LENGTH_SHORT).show();
                } else if (spinner.getSelectedItem().equals("")||spinner.getSelectedItem()==null) {
                    Toast.makeText(TelaPrincipal.this, "Selecioner um Produto!", Toast.LENGTH_SHORT).show();
                } else {
                    String nomeProdutoEscolhido = String.valueOf(spinner.getSelectedItem());
                        listaProduto_entre_datas(datai,dataf,nomeProdutoEscolhido);
                    d.dismiss();
                }
            }
        });d.show();

    }
    public void exibirLayoutEntreDatas(){
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.layoutentredatas);
        TextView campoDataInicio =d.findViewById(R.id.editDataInicio);
        TextView campoDataFinal =d.findViewById(R.id.editDataFinal);
        AppCompatButton btEntreDatas =d.findViewById(R.id.botaoEntreDatas);
        campoDataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setaCalendario(campoDataInicio);
            }
        });
        campoDataFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setaCalendario(campoDataFinal);
            }
        });
        btEntreDatas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String datai=campoDataInicio.getText().toString();
                String dataf=campoDataFinal.getText().toString();
                if(datai.equals("")) {
                    Toast.makeText(TelaPrincipal.this, "Preencha o campo data início!", Toast.LENGTH_SHORT).show();
                }else if(dataf.equals("")){
                    Toast.makeText(TelaPrincipal.this, "Preencha o campo data final!", Toast.LENGTH_SHORT).show();
                }else{
                    listaEntreAsDatas(datai,dataf);
                    d.dismiss();
                }
            }
        });d.show();
    }
    public void exibirLayoutPorProduto(){
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.layoutporproduto);
        AppCompatButton btProd =d.findViewById(R.id.botaoacaoProduto);
       Spinner  spinner = d.findViewById(R.id.escolheProdutoUnico);
       listaNomeProdutos(spinner);
        btProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinner.getSelectedItem().equals("")||spinner==null) {
                    Toast.makeText(TelaPrincipal.this, "Selecioner um Produto!", Toast.LENGTH_SHORT).show();
                }else{
                  String nomeProduto=String.valueOf(spinner.getSelectedItem().toString());
                  listaProdutoEspecifico(nomeProduto);
                    d.dismiss();
                }
            }
        });d.show();
    }
    public void listaPelaData (String dat) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(TelaPrincipal.this,ListaPorData.class);
                i.putExtra("data",dat);
                startActivity(i);

            }
        }).start();
    }
    public void listaProdutoEspecifico (String nomeProd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(TelaPrincipal.this,ListaPorProduto.class);
                i.putExtra("nomeProd",nomeProd);
                startActivity(i);
            }
        }).start();
    }
    public void listaEntreAsDatas (String datai,String dataf) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(TelaPrincipal.this,ListaEntreDatas.class);
                i.putExtra("datai",datai);
                i.putExtra("dataf",dataf);
                startActivity(i);
            }
        }).start();
    }
    public void listaPeloNomeProduto_Data (String data,String nomeProd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(TelaPrincipal.this, ListaPorDataProduto.class);
                i.putExtra("nomeProd",nomeProd);
                i.putExtra("dataunica",data);
                startActivity(i);
            }
        }).start();
    }
    public void listaProduto_entre_datas (String datai,String dataf,String nomeProd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(TelaPrincipal.this, ListaEntreDatasProduto.class);
                i.putExtra("nomeProduto",nomeProd);
                i.putExtra("datacomeco",datai);
                i.putExtra("datatermino",dataf);
                startActivity(i);
            }
        }).start();
    }
    public void setaCalendario(TextView t){
        // As variavésis com os números do ano mês e dia
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        // método instânciado na linha de baixo para executar a exibição do calendário
        DatePickerDialog datePickerDialog = new DatePickerDialog(TelaPrincipal.this, new DatePickerDialog.OnDateSetListener() {
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
    public void listaNomeProdutos(Spinner spinner){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();
                    MyDao myDao = bd.myDao();
                    List<String> listaNomesProdutos = myDao.listaProdutosNomes();
                    if(listaNomesProdutos.isEmpty()){
                        listaNomesProdutos.add(0,"Sem Produto!");
                    }else{
                        listaNomesProdutos.add(0,"");
                    }
                    ArrayAdapter<String> listaNomeProd = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,listaNomesProdutos );
                    spinner.setAdapter(listaNomeProd);
                }catch (Exception r){
                }
            }
        }).start();

    }
}