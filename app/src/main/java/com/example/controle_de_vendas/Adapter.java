package com.example.controle_de_vendas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.controle_de_vendas.DaoBd.MyDao;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.Modelo.Investimento;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyholderProdutos> {

    public Adapter(){;
    }
    private List<Investimento> listaInvesti;
    public Adapter(List<Investimento> listaInvesti) {
        this.listaInvesti = listaInvesti;
    }
    @NonNull
    @Override
    public MyholderProdutos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produtoespecifico,parent,false);
        return new MyholderProdutos(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyholderProdutos holder, int position) {
     holder.idInvestir.setText(""+listaInvesti.get(position).getId());
      holder.nome.setText(listaInvesti.get(position).getNomeProd());
      holder.qtd.setText(""+listaInvesti.get(position).getQuantidade());
      holder.data.setText(listaInvesti.get(position).getData());
      holder.preco.setText("R$ "+ holder.formataValor(listaInvesti.get(position).getValorRv()));
      holder.ValorPagor.setText("R$ "+holder.formataValor(listaInvesti.get(position).getPrecoPg()));
      holder.todototal.setText("R$ "+ holder.formataValor(listaInvesti.get(position).getTodoTotal()));

      int id=listaInvesti.get(position).getId();
      String nom=listaInvesti.get(position).getNomeProd();
      String dat=listaInvesti.get(position).getData();
      int quantidad=listaInvesti.get(position).getQuantidade();
      double vlRv=listaInvesti.get(position).getValorRv();
      double vlPg=listaInvesti.get(position).getPrecoPg();
      double totl=listaInvesti.get(position).getTodoTotal();
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              MyBancoControle_venda  bd = Room.databaseBuilder(holder.delete.getContext(), MyBancoControle_venda.class, "Meu_bd").allowMainThreadQueries().build();
                MyDao myDao = bd.myDao();
                if(myDao.is_exist(id)){
                    AlertDialog.Builder del= new AlertDialog.Builder(holder.delete.getContext());
                    del.setTitle("Excluir Dados:");
                    del.setMessage("Tem certeza, que quer excluir?");
                    del.setCancelable(false);
                  // del.setIcon(R.drawable.);
                    del.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(holder.itemView.getContext(), "Dados excluídos com sucesso!", Toast.LENGTH_SHORT).show();
                            myDao.deletaDados(id);notifyDataSetChanged();
                            Intent intents = new Intent(holder.itemView.getContext(),MainActivity.class);
                            holder.itemView.getContext().startActivity(intents);
                        }
                    });
                    del.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(holder.itemView.getContext(), "Dados não excluidos!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    del.create();
                    del.show();
                }else{
                    Toast.makeText(holder.itemView.getContext(), "Não existir dados para excluir!", Toast.LENGTH_SHORT).show();
                }
            }
        });
      holder.alterar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              MyBancoControle_venda  bd = Room.databaseBuilder(holder.alterar.getContext(), MyBancoControle_venda.class, "Meu_bd").allowMainThreadQueries().build();
              MyDao myDao = bd.myDao();
            boolean vrfc=myDao.is_exist(id);
              if (vrfc) {
                 Intent intents = new Intent(holder.itemView.getContext(),Investimentos.class);
                 intents.putExtra("id",id);
                intents.putExtra("nome", nom);
                  intents.putExtra("data", dat);
                  intents.putExtra("qtd", quantidad);
                  intents.putExtra("precoRv", vlRv);
                  intents.putExtra("valorPg", vlPg);
                  intents.putExtra("total", totl);
                  holder.itemView.getContext().startActivity(intents);
              }else {
                  Toast.makeText(holder.itemView.getContext(), "Não tem dados salvos!", Toast.LENGTH_SHORT).show();
              }
          }
      });
    }
    @Override
    public int getItemCount() {
        return listaInvesti.size();
    }
    public class MyholderProdutos extends RecyclerView.ViewHolder{
       TextView todototal,idInvestir,nome,qtd,preco,data,ValorPagor;
       Button alterar, delete;
        public String formataValor(double valor){
            //  classe DecimaFormat para colocar os valores em casas decimais
            DecimalFormat decimalFormat  = new DecimalFormat("#,##0.00");
            String valorConvertido=decimalFormat.format(valor);
            return valorConvertido;
        }
        public MyholderProdutos(@NonNull View itemView) {
            super(itemView);
            nome =itemView.findViewById(R.id.codNome);
            qtd =itemView.findViewById(R.id.QuantidadeList);
            preco =itemView.findViewById(R.id.PrecoProd);
            data =itemView.findViewById(R.id.Data);
            alterar=itemView.findViewById(R.id.alterar_prod);
            todototal=itemView.findViewById(R.id.TodoTotal);
            ValorPagor=itemView.findViewById(R.id.valorPg);
            idInvestir=itemView.findViewById(R.id.idInvetimento);
            delete=itemView.findViewById(R.id.buttonDelete);
        }
    }

}
