package br.com.fidelizacao.fidelizacao.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.fidelizacao.R;
import br.com.fidelizacao.fidelizacao.Model.Cliente;


public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ClientesViewHolder> {
    private List<Cliente> clientes;
    private final OnClientesListener clientesListener;
    private final Context context;

    public ClientesAdapter(List<Cliente> clientes, OnClientesListener clientesListener, Context context) {
        this.clientes = clientes;
        this.clientesListener = clientesListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ClientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflando a View
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.adapter_clientes, parent, false);


       return new ClientesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientesViewHolder holder, int position) {
        final Cliente cliente = clientes.get(position);
        holder.tvNomeCliente.setText(cliente.getNome());

        if(clientesListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clientesListener.onClickCliente(v, cliente);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class ClientesViewHolder extends RecyclerView.ViewHolder{
        TextView tvNomeCliente;
        ConstraintLayout constraintLayout;

        public ClientesViewHolder(View view){
            super(view);
            tvNomeCliente = view.findViewById(R.id.tvNomeCliente);
            constraintLayout = view.findViewById(R.id.constraintListClientes);
        }
    }

    public interface OnClientesListener{
        public void onClickCliente(View view, Cliente cliente);
    }

}
