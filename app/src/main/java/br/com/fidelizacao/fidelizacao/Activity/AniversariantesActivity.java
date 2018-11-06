package br.com.fidelizacao.fidelizacao.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import br.com.fidelizacao.R;
import br.com.fidelizacao.fidelizacao.Adapter.AniversariantesAdapter;
import br.com.fidelizacao.fidelizacao.Model.Cliente;
import br.com.fidelizacao.fidelizacao.RestAdress.RestAddress;
import br.com.fidelizacao.fidelizacao.Task.HandlerTask;
import br.com.fidelizacao.fidelizacao.Task.TaskRest;
import br.com.fidelizacao.fidelizacao.Util.JsonParser;

public class AniversariantesActivity extends AppCompatActivity {
    private Context context;
    private List<Cliente> aniversariantes;
    private RecyclerView recyclerViewAniversariantes;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniversariantes);

        context = this;



        toolbar = findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Aniversariantes");
        }

        aniversariantes = new ArrayList<>();

        recyclerViewAniversariantes = findViewById(R.id.recyclerViewAniversariantes);

        //Forma como os elementos serão mostrados
        recyclerViewAniversariantes.setLayoutManager(new LinearLayoutManager(context));

        recyclerViewAniversariantes.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new TaskRest(TaskRest.RequestMethod.GET, handlerTaskAniversariantes).execute(RestAddress.BUSCAR_ANIVERSARIANTES);
    }

    private HandlerTask handlerTaskAniversariantes = new HandlerTask() {
        @Override
        public void onPreHandle() {

        }

        @Override
        public void onSuccess(String valueRead) {
            //Transformando a String em lista de Clientes
            aniversariantes = new JsonParser<>(Cliente.class).toList(valueRead, Cliente[].class);
            recyclerViewAniversariantes.setAdapter(new AniversariantesAdapter(aniversariantes, aniversariantesListener, context));

        }

        @Override
        public void onError(Exception erro) {
            Toast.makeText(context, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private AniversariantesAdapter.OnAniversariantesListener aniversariantesListener = new AniversariantesAdapter.OnAniversariantesListener() {
        @Override
        public void onClickProcedimento(View view, Cliente cliente) {
            Toast.makeText(context, cliente.getNome() + " selecionado", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
