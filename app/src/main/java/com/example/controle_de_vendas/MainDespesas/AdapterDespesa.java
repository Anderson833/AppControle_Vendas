package com.example.controle_de_vendas.MainDespesas;

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

import com.example.controle_de_vendas.DaoBd.MyDaoDespesa;
import com.example.controle_de_vendas.Database.MyBancoControle_venda;
import com.example.controle_de_vendas.Modelo.Despesas;
import com.example.controle_de_vendas.R;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterDespesa extends RecyclerView.Adapter<AdapterDespesa.MyholderDespesas> {

    private List<Despesas> listaDespesa;

    public AdapterDespesa(List<Despesas> listaDespesa) {
        this.listaDespesa = listaDespesa;
    }

    public AdapterDespesa(){
    }

    @NonNull
    @Override
    public MyholderDespesas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutlistadespesas,parent,false);
        return new MyholderDespesas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyholderDespesas holder, int position) {
        holder.idDesp.setText(""+listaDespesa.get(position).getId());
        holder.descricao.setText(""+listaDespesa.get(position).getNomeDespesa());
        holder.todototalDesp.setText("R$ "+ holder.formataValor(listaDespesa.get(position).getTotalDesp()));
        holder.data.setText(""+listaDespesa.get(position).getData_despesas());

        int id=listaDespesa.get(position).getId();
        String descricao=listaDespesa.get(position).getNomeDespesa();
        String data=listaDespesa.get(position).getData_despesas();
        double total=listaDespesa.get(position).getTotalDesp();

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBancoControle_venda bd = Room.databaseBuilder(holder.delete.getContext(), MyBancoControle_venda.class, "Meu_bd").allowMainThreadQueries().build();
                MyDaoDespesa myDaoDesp = bd.myDaoDespesa();
                if(myDaoDesp.is_exist(id)){
                    AlertDialog.Builder del= new AlertDialog.Builder(holder.delete.getContext());
                    del.setTitle("Excluir Dados:");
                    del.setMessage("Tem certeza, que quer excluir?");
                    del.setCancelable(false);
                    // del.setIcon(R.drawable.);
                    del.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(holder.itemView.getContext(), "Dados excluídos com sucesso!", Toast.LENGTH_SHORT).show();
                            myDaoDesp.deletaUmRgDespesa(id);notifyDataSetChanged();
                            Intent intents = new Intent(holder.itemView.getContext(), ListaDespesas.class);
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
                MyDaoDespesa myDaoDesp = bd.myDaoDespesa();
                boolean vrfc=myDaoDesp.is_exist(id);
                if (vrfc) {
                    Intent intents = new Intent(holder.itemView.getContext(), DespesasMain.class);
                    intents.putExtra("id",id);
                    intents.putExtra("descricao", descricao);
                    intents.putExtra("data",data);
                    intents.putExtra("total",total);
                    holder.itemView.getContext().startActivity(intents);
                }else {
                    Toast.makeText(holder.itemView.getContext(), "Não tem dados salvos!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDespesa.size();
    }

    public class MyholderDespesas extends RecyclerView.ViewHolder{
        TextView todototalDesp,descricao,data, idDesp;
        Button alterar, delete;
        public String formataValor(double valor){
            //  classe DecimaFormat para colocar os valores em casas decimais
            DecimalFormat decimalFormat  = new DecimalFormat("#,##0.00");
            String valorConvertido=decimalFormat.format(valor);
            return valorConvertido;
        }
        public MyholderDespesas(@NonNull View itemView) {
            super(itemView);
          todototalDesp =itemView.findViewById(R.id.TodoTotalDespesas);
          descricao=itemView.findViewById(R.id.DESCRICAO);
          data = itemView.findViewById(R.id.Datadesp);
          alterar=itemView.findViewById(R.id.alterar_DESPESA);
          delete =itemView.findViewById(R.id.buttonDeleteDespesa);
          idDesp=itemView.findViewById(R.id.idDespesa);
        }
    }
}
