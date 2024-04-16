package com.example.controle_de_vendas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.controle_de_vendas.DaoBd.MyDao;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.Modelo.Investimento;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private int id;
    private AppCompatButton botaoDt, botaoListaProd, botaoEntreDatas,botaoEntreDataProduto;
    private CheckBox checkdataespecifica, checkBoxProd,checkBoxEntreDatas;
    private Spinner opcaoDeEscolhaProd,escolheProd;
    private LinearLayoutCompat linearDataEspecifica,linearProdutosNomes ,linearEntreDatas,linearEntreDataProduto;
    private TextView totalLista,totalListaRg, editDataEspecifica, datai,dataf,dataUnica;
   private MyBancoControle_venda bd;
   private  String dat="";
   Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.Recycleviewprodutos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        checkdataespecifica =findViewById(R.id.checkBoxData);
        checkBoxProd=findViewById(R.id.checkBoxProduto);
        linearDataEspecifica = findViewById(R.id.dataEspecificaLinear);
        linearProdutosNomes = findViewById(R.id.ProdutoEspecificaLinear);
        linearEntreDataProduto =findViewById(R.id.entredatasProduto);
        totalListaRg = findViewById(R.id.totalListaRg);
        opcaoDeEscolhaProd = findViewById(R.id.selecionaProdutos);
        escolheProd=findViewById(R.id.escolheProduto);
        editDataEspecifica=findViewById(R.id.editDataEspecifica);
        totalLista=findViewById(R.id.totalLista);
        linearEntreDatas=findViewById(R.id.entredatasLinear);
        datai =findViewById(R.id.editDataInicio);
        dataf =findViewById(R.id.editDataFinal);
        dataUnica = findViewById(R.id.editDataEspecificaProd);
        checkBoxEntreDatas=findViewById(R.id.checkBoxEntreDatas);
        // Nas lista de baixo estão os botões dos campos
        botaoDt =findViewById(R.id.botaoDataEpcfc);
        botaoListaProd=findViewById(R.id.botaoProdEpcfc);
        botaoEntreDatas = findViewById(R.id.botaoEntreDatas);
        botaoEntreDataProduto = findViewById(R.id.botaoEntreDataProduto);
        calendar=Calendar.getInstance();
             
        listaTodosDados();
        listaNomeProdutos(opcaoDeEscolhaProd);
        listaNomeProdutoEspecifico();

        checkdataespecifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(checkBoxProd.isChecked() && checkdataespecifica.isChecked()) {
                   linearDataEspecifica.setVisibility(View.GONE);
                   linearProdutosNomes.setVisibility(View.GONE);
                   linearEntreDataProduto.setVisibility(View.VISIBLE);
               }else if(checkdataespecifica.isChecked()){
                       linearDataEspecifica.setVisibility(View.VISIBLE);
                }else {
                    linearEntreDataProduto.setVisibility(View.GONE);
                   linearDataEspecifica.setVisibility(View.GONE);
                  if(!checkdataespecifica.isChecked()&&checkBoxProd.isChecked()&&!checkBoxEntreDatas.isChecked()){
                       linearProdutosNomes.setVisibility(View.VISIBLE);
                   }
                }


            }
        });

        checkBoxEntreDatas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxEntreDatas.isChecked()){
                    linearEntreDatas.setVisibility(View.VISIBLE);
                }else {
                    linearEntreDatas.setVisibility(View.GONE);
                }
            }
        });

        checkBoxProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxProd.isChecked() && checkdataespecifica.isChecked()) {
                    linearDataEspecifica.setVisibility(View.GONE);
                    linearProdutosNomes.setVisibility(View.GONE);
                    linearEntreDataProduto.setVisibility(View.VISIBLE);
                }else if(checkBoxProd.isChecked()){
                        linearProdutosNomes.setVisibility(View.VISIBLE);
                }else {
                    linearProdutosNomes.setVisibility(View.GONE);
                    linearEntreDataProduto.setVisibility(View.GONE);
                   if(!checkBoxProd.isChecked()&&checkdataespecifica.isChecked()&& !checkBoxEntreDatas.isChecked()){
                        linearDataEspecifica.setVisibility(View.VISIBLE);
                    }
                }


            }
        });

        botaoDt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editDataEspecifica.getText().toString().equals("") || editDataEspecifica.getText().toString()==null){
                }else{
                    Log.i("bt","da ta é "+dat);
                  listaPelaData(dat);
                   // lista();
                    setaQtdRegistros_Total(dat);
                }
            }
        });

        botaoEntreDatas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(datai.getText().toString().equals("") || datai.getText().toString()==null){
                }else if(dataf.getText().toString().equals("") || dataf.getText().toString()==null){
                }else{
                       listaEntreAsDatas(datai.getText().toString(),dataf.getText().toString());
            }}
        });

        botaoListaProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String escolha=String.valueOf(opcaoDeEscolhaProd.getSelectedItem().toString());
                if(escolha.equals("") || escolha==null){

                }else{
                    Log.i("bt","nome prod é  "+escolha);
                    listaPeloNomeProduto(escolha);

                }
            }
        });

        botaoEntreDataProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String escolha=String.valueOf(escolheProd.getSelectedItem().toString());
                if(escolha.equals("") || escolha==null){
                }else{
                    listaPeloNomeProduto_Data(dataUnica.getText().toString(),escolha);
                }
            }
        });
         dataUnica.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 // As variavésis com os números do ano mês e dia
                 int year=calendar.get(Calendar.YEAR);
                 int month=calendar.get(Calendar.MONTH);
                 int day=calendar.get(Calendar.DAY_OF_MONTH);
                 // método instânciado na linha de baixo para executar a exibição do calendário
                 DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                         dataUnica.setText(dat);
                     }
                 },year,month,day);datePickerDialog.show();

             }
         });
        editDataEspecifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // As variavésis com os números do ano mês e dia
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                // método instânciado na linha de baixo para executar a exibição do calendário
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        editDataEspecifica.setText(dat);
                    }
                },year,month,day);datePickerDialog.show();

            }
        });
        datai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // As variavésis com os números do ano mês e dia
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                // método instânciado na linha de baixo para executar a exibição do calendário
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        datai.setText(dat);
                    }
                },year,month,day);datePickerDialog.show();

            }
        });
        dataf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // As variavésis com os números do ano mês e dia
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                // método instânciado na linha de baixo para executar a exibição do calendário
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        dataf.setText(dat);
                    }
                },year,month,day);datePickerDialog.show();

            }
        });
    }
    public  void listaNomeProdutos(Spinner spinner){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();
                    MyDao myDao = bd.myDao();
                    List<String> listaNomesProdutos = myDao.listaProdutosNomes();
                    listaNomesProdutos.add(0,"");
                    ArrayAdapter<String> listaNomeProd = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,listaNomesProdutos );
                    spinner.setAdapter(listaNomeProd);
                }catch (Exception r){

                }

            }
        }).start();
    }
    public  void listaNomeProdutoEspecifico(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();
                    MyDao myDao = bd.myDao();
                    List<String> listaNomesProdutos = myDao.listaProdutosNomes();
                    listaNomesProdutos.add(0,"");
                    ArrayAdapter<String> listaNomeProduto = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,listaNomesProdutos );
                    escolheProd.setAdapter(listaNomeProduto);
                    Log.i("spinner",""+listaNomeProduto.toString());
                }catch (Exception r){

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
           public void lista () {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").build();
                    MyDao myDao = bd.myDao();
                    List<Investimento> listInvestir = myDao.getAllInvestimentos();
                    Adapter adapter = new Adapter(listInvestir);
                    recyclerView.setAdapter(adapter);
                }
            }).start();
        }

    public void listaPelaData (String dat) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,ListaPorData.class);
                i.putExtra("data",dat);
                startActivity(i);
            }
        }).start();
    }
    public void listaEntreAsDatas (String datai,String dataf) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,ListaEntreDatas.class);
                i.putExtra("datai",datai);
                i.putExtra("dataf",dataf);
                startActivity(i);
            }
        }).start();
    }

    public void listaPeloNomeProduto (String nomeProd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,ListaPorProduto.class);
                i.putExtra("nomeProd",nomeProd);
                startActivity(i);
            }
        }).start();
    }
    public void listaPeloNomeProduto_Data (String data,String nomeProd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, ListaPorDataProduto.class);
                i.putExtra("nomeProd",nomeProd);
                i.putExtra("dataunica",data);
                startActivity(i);
            }
        }).start();
    }
    public void setaTotal(){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").allowMainThreadQueries().build();
                 MyDao myDao = bd.myDao();
               double total=  myDao.todoTotalInvestido();
               totalLista.setText(formataValor(total));
             }
         }).start();
    }

    public void setaQtdRegistros(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").allowMainThreadQueries().build();
                MyDao myDao = bd.myDao();
                String qtdRg=  myDao.qtdRegistros();
                totalListaRg.setText(""+qtdRg);
            }
        }).start();
    }

    public void setaQtdRegistros_Total(String data){
        new Thread(new Runnable() {
            @Override
            public void run() {
                bd = Room.databaseBuilder(getApplication(), MyBancoControle_venda.class, "Meu_bd").allowMainThreadQueries().build();
                MyDao myDao = bd.myDao();
                String qtdRg=  myDao.qtdRegistrosPelaData(data);
                totalListaRg.setText(""+qtdRg);
                double total=  myDao.totalInvestidoPelaData(data);
                totalLista.setText(formataValor(total));
            }
        }).start();
    }

      public  void listaTodosDados(){
        if(!checkdataespecifica.isChecked()&&!checkBoxProd.isChecked()&&!checkBoxEntreDatas.isChecked()){
            lista();
            setaTotal();
            setaQtdRegistros();
        }
      }

    }
