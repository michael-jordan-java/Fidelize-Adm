package br.com.fidelizacao.fidelizacao.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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

public class GanhadoresActivity extends AppCompatActivity {
    private Context context;
    private List<Cliente> clientes;
    private Toolbar toolbar;
    private RecyclerView recyclerViewGanhadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganhadores);

        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Ganhadores da promoção");
        }

        recyclerViewGanhadores = findViewById(R.id.recyclerGanhadores);

        //Forma como os elementos serão mostrados
        recyclerViewGanhadores.setLayoutManager(new LinearLayoutManager(context));

        recyclerViewGanhadores.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new TaskRest(TaskRest.RequestMethod.GET, handlerTaskGanhadores).execute(RestAddress.BUSCAR_GANHADORES);
    }

    private HandlerTask handlerTaskGanhadores = new HandlerTask() {
        @Override
        public void onPreHandle() {

        }

        @Override
        public void onSuccess(String valueRead) {
            //Transformando a String em lista de Clientes
            clientes = new JsonParser<>(Cliente.class).toList(valueRead, Cliente[].class);
            if (clientes.isEmpty()) {
                TextView tvTemGanhadores = findViewById(R.id.tvTemGanhadores);
                tvTemGanhadores.setVisibility(View.VISIBLE);
                recyclerViewGanhadores.setVisibility(View.INVISIBLE);
            }

            recyclerViewGanhadores.setAdapter(new AniversariantesAdapter(clientes, ganhadoresListener, context));
        }

        @Override
        public void onError(Exception erro) {
            erro.printStackTrace();
            Toast.makeText(context, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private AniversariantesAdapter.OnAniversariantesListener ganhadoresListener = new AniversariantesAdapter.OnAniversariantesListener() {
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
