package br.com.fidelizacao.fidelizacao.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.method.MultiTapKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import br.com.fidelizacao.R;
import br.com.fidelizacao.fidelizacao.Model.Cliente;
import br.com.fidelizacao.fidelizacao.RestAdress.RestAddress;
import br.com.fidelizacao.fidelizacao.Task.HandlerTask;
import br.com.fidelizacao.fidelizacao.Task.TaskRest;

public class AniversariantesAdapter extends RecyclerView.Adapter<AniversariantesAdapter.AniversariantesViewHolder> {
    private List<Cliente> clientes;
    private final OnAniversariantesListener aniversariantesListener;
    private final Context context;
    private String qtdCompra;

    public AniversariantesAdapter(List<Cliente> clientes, OnAniversariantesListener aniversariantesListener, Context context) {
        this.clientes = clientes;
        this.aniversariantesListener = aniversariantesListener;
        this.context = context;
    }

    @NonNull
    @Override
    public AniversariantesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflando a View
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.adapter_aniversariantes, parent, false);


       return new AniversariantesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AniversariantesViewHolder holder, int position) {
        final Cliente cliente = clientes.get(position);
        new TaskRest(TaskRest.RequestMethod.GET, handlerQtdCompra).execute(RestAddress.BUSCAR_QTD_FIDELIZACAO + cliente.getClienteId());
        holder.tvNome.setText(cliente.getNome());

        // Adicionando formato dd/MM/yyyy
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        holder.tvDataNascimento.setText(formato.format(cliente.getDataNascimento()));
        //holder.tvQtdCompra.setText(qtdCompra);

        if(aniversariantesListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    aniversariantesListener.onClickProcedimento(v, cliente);
                }
            });
        }

        holder.ivSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exibirMensagemEdt(cliente.getNome(), context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class AniversariantesViewHolder extends RecyclerView.ViewHolder{
        TextView tvNome, tvDataNascimento, tvQtdCompra;
        ConstraintLayout constraintLayout;
        ImageView ivSendMessage;

        public AniversariantesViewHolder(View view){
            super(view);
            tvNome = view.findViewById(R.id.tvNome);
            tvDataNascimento = view.findViewById(R.id.tv_dt_aniversario);
            tvQtdCompra = view.findViewById(R.id.tv_qtd_compra);
            constraintLayout = view.findViewById(R.id.recyclerLayout);
            ivSendMessage = view.findViewById(R.id.ivSendMessage);
        }
    }

    public interface OnAniversariantesListener{
        public void onClickProcedimento(View view, Cliente cliente);
    }

    public HandlerTask handlerQtdCompra = new HandlerTask() {
        @Override
        public void onPreHandle() {

        }

        @Override
        public void onSuccess(String valueRead) {
            qtdCompra = valueRead;
        }

        @Override
        public void onError(Exception erro) {

        }
    };

    public void exibirMensagemEdt(String titulo, final Context context){

        AlertDialog.Builder mensagem = new AlertDialog.Builder(context);
        mensagem.setTitle(titulo);

        // DECLARACAO DO EDITTEXT
        final EditText input = new EditText(context);

        mensagem.setView(input);
        mensagem.setNeutralButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(context, input.getText().toString().trim(),
                        Toast.LENGTH_SHORT).show();

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Esse é o texto para o Michael Jordan");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                context.startActivity(sendIntent);
            }

        });

        mensagem.show();
    }
}
