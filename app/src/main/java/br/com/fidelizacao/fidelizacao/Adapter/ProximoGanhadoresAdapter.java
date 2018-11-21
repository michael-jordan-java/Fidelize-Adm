package br.com.fidelizacao.fidelizacao.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.fidelizacao.R;
import br.com.fidelizacao.fidelizacao.Model.Cliente;
import br.com.fidelizacao.fidelizacao.RestAdress.RestAddress;
import br.com.fidelizacao.fidelizacao.Task.HandlerTask;
import br.com.fidelizacao.fidelizacao.Task.TaskRest;

public class ProximoGanhadoresAdapter extends RecyclerView.Adapter<ProximoGanhadoresAdapter.ProximoGanhadoresViewHolder> {
    private List<Cliente> clientes;
    private final OnProximoGanhadoresListener proximoGanhadoresListener;
    private final Context context;
    private String qtdCompra;

    public ProximoGanhadoresAdapter(List<Cliente> clientes, OnProximoGanhadoresListener proximoGanhadoresListener, Context context) {
        this.clientes = clientes;
        this.proximoGanhadoresListener = proximoGanhadoresListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ProximoGanhadoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflando a View
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.adapter_proximo_ganhadores, parent, false);


        return new ProximoGanhadoresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProximoGanhadoresViewHolder holder, int position) {
        final Cliente cliente = clientes.get(position);
        new TaskRest(TaskRest.RequestMethod.GET, handlerQtdCompra).execute(RestAddress.BUSCAR_QTD_FIDELIZACAO + cliente.getClienteId());
        holder.tvNome.setText(cliente.getNome());

        //holder.tvQtdCompra.setText(qtdCompra);

        if (proximoGanhadoresListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    proximoGanhadoresListener.onClickProcedimento(v, cliente);
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

    public class ProximoGanhadoresViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvDataNascimento, tvQtdCompra;
        ConstraintLayout constraintLayout;
        ImageView ivSendMessage;

        public ProximoGanhadoresViewHolder(View view) {
            super(view);
            tvNome = view.findViewById(R.id.tvNome);
            tvQtdCompra = view.findViewById(R.id.tv_qtd_compra);
            constraintLayout = view.findViewById(R.id.recyclerLayout);
            ivSendMessage = view.findViewById(R.id.ivSendMessage);
        }
    }

    public interface OnProximoGanhadoresListener {
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

}
