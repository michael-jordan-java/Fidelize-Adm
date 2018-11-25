package br.com.fidelizacao.fidelizacao.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.com.fidelizacao.R;


public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
    }

    public void opcaoClick(View view){
        switch (view.getId()){
            case R.id.ivCadastro:
                startActivity(new Intent(context, CadastroFidelizacaoActivity.class));
                break;
            case R.id.ivAniversariantes:
                startActivity(new Intent(context, AniversariantesActivity.class));
                break;
            case R.id.ivClientes:
                startActivity(new Intent(context, ClientesActivity.class));
                break;
            case R.id.ivGanhadores:
                startActivity(new Intent(context, GanhadoresActivity.class));
                break;
            case R.id.ivRelatorios:
                startActivity(new Intent(context, RelatoriosActivity.class));
                break;
        }
    }

}
