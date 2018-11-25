package br.com.fidelizacao.fidelizacao.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        holder.tvNome.setText(cliente.getNome());

        // Adicionando formato dd/MM/yyyy
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        holder.tvDataNascimento.setText(formato.format(cliente.getDataNascimento()));
        //holder.tvQtdCompra.setText(qtdCompra);

        if (aniversariantesListener != null) {
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
                String toNumber = cliente.getCelular(); // Replace with mobile phone number without +Sign or leading zeros.

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class AniversariantesViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvDataNascimento;
        LinearLayout constraintLayout;
        ImageView ivSendMessage;

        public AniversariantesViewHolder(View view) {
            super(view);
            tvNome = view.findViewById(R.id.tvNome);
            tvDataNascimento = view.findViewById(R.id.tv_dt_aniversario);
            constraintLayout = view.findViewById(R.id.recyclerLayout);
            ivSendMessage = view.findViewById(R.id.ivSendMessage);
        }
    }

    public interface OnAniversariantesListener {
        public void onClickProcedimento(View view, Cliente cliente);
    }
}
