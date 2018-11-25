package br.com.fidelizacao.fidelizacao.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import br.com.fidelizacao.R;
import br.com.fidelizacao.fidelizacao.Adapter.AniversariantesAdapter;
import br.com.fidelizacao.fidelizacao.Adapter.ClientesAdapter;
import br.com.fidelizacao.fidelizacao.Model.Cliente;
import br.com.fidelizacao.fidelizacao.RestAdress.RestAddress;
import br.com.fidelizacao.fidelizacao.Task.HandlerTask;
import br.com.fidelizacao.fidelizacao.Task.TaskRest;
import br.com.fidelizacao.fidelizacao.Util.JsonParser;

public class ClientesActivity extends AppCompatActivity {
    private Context context;
    private List<Cliente> clientes;
    private RecyclerView recyclerViewCliente;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        context = this;

        toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerViewCliente = findViewById(R.id.recyclerClientes);

        //Forma como os elementos serão mostrados
        recyclerViewCliente.setLayoutManager(new LinearLayoutManager(context));

        recyclerViewCliente.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new TaskRest(TaskRest.RequestMethod.GET, handlerClientes).execute(RestAddress.BUSCAR_CLIENTES);
    }

    private HandlerTask handlerClientes = new HandlerTask() {
        @Override
        public void onPreHandle() {

        }

        @Override
        public void onSuccess(String valueRead) {
            //Transformando a String em lista de Clientes
            clientes = new JsonParser<>(Cliente.class).toList(valueRead, Cliente[].class);
            recyclerViewCliente.setAdapter(new AniversariantesAdapter(clientes, clientesListener, context));
        }

        @Override
        public void onError(Exception erro) {
            erro.printStackTrace();
            Toast.makeText(context, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    /*  private ClientesAdapter.OnClientesListener clientesListener = new ClientesAdapter.OnClientesListener() {
          @Override
          public void onClickCliente(View view, Cliente cliente) {

          }
      };
      */
    private AniversariantesAdapter.OnAniversariantesListener clientesListener = new AniversariantesAdapter.OnAniversariantesListener() {
        @Override
        public void onClickProcedimento(View view, Cliente cliente) {
            Toast.makeText(context, cliente.getNome() + " selecionado", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
