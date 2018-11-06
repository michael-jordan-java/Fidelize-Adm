package br.com.fidelizacao.fidelizacao.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.fidelizacao.R;
import br.com.fidelizacao.fidelizacao.Model.Adm;
import br.com.fidelizacao.fidelizacao.Model.ProgramaFidelizacao;
import br.com.fidelizacao.fidelizacao.Model.TipoFidelizacao;
import br.com.fidelizacao.fidelizacao.RestAdress.RestAddress;
import br.com.fidelizacao.fidelizacao.Task.HandlerTask;
import br.com.fidelizacao.fidelizacao.Task.HandlerTaskAdapter;
import br.com.fidelizacao.fidelizacao.Task.TaskRest;
import br.com.fidelizacao.fidelizacao.Util.JsonParser;
import br.com.fidelizacao.fidelizacao.Util.PrefsUtil;

public class CadastroFidelizacaoActivity extends AppCompatActivity {
    private Context context;
    private TextInputEditText etDataExpiracao, etQtdPremio;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private int ano, mes, dia;
    private Spinner spinner;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_fidelizacao);

        context = this;
        progressDialog = new ProgressDialog(context);
        etDataExpiracao = findViewById(R.id.etDataExpiracao);
        etQtdPremio = findViewById(R.id.etQtdPremio);

        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        spinner = findViewById(R.id.spinnerTipoFidelizacao);
        spinner.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);

        etDataExpiracao.setFocusable(false);
        etQtdPremio.setFocusable(false);
        spinner.setFocusable(true);

    }


    @Override
    protected void onStart() {
        super.onStart();

        showSpinner();

        calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etDataExpiracao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        etDataExpiracao.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void salvarOnClick(View view) {
        String spinnerItem = spinner.getSelectedItem().toString().trim();
        String dataExpiracao = etDataExpiracao.getText().toString().trim();
        String qtdPremio = etQtdPremio.getText().toString().trim();
        ProgramaFidelizacao controleFidelidade = new ProgramaFidelizacao();


        if (spinnerItem.isEmpty() || dataExpiracao.isEmpty() || qtdPremio.isEmpty()) {
            Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        } else {
            // Verificando se a data de expiração é menor que a data atual
            Date tempoExpiracao = new Date(dataExpiracao);
            if(!tempoExpiracao.after(new Date())) {
                Toast.makeText(context, "O tempo de expiração não pode ser menor que a data atual", Toast.LENGTH_SHORT).show();
            }else {
                controleFidelidade.setTempoExpiracao(tempoExpiracao);
                controleFidelidade.setTipoFidelizacao(TipoFidelizacao.valueOf(spinnerItem));
                controleFidelidade.setQtd_premio(Integer.parseInt(qtdPremio));
                controleFidelidade.setUsuario_cadastro(PrefsUtil.getLogin(context));

                new TaskRest(TaskRest.RequestMethod.POST, handlerSalvarFidelidade).execute(RestAddress.CADASTRAR_FIDELIDADE, new JsonParser<>(ProgramaFidelizacao.class).fromObject(controleFidelidade));
            }
        }
    }

    private HandlerTask handlerSalvarFidelidade = new HandlerTaskAdapter() {
        @Override
        public void onPreHandle() {
            super.onPreHandle();
            progressDialog.setTitle("Aguarde um momento");
            progressDialog.setMessage("Salvando...");
            progressDialog.show();
        }

        @Override
        public void onSuccess(String valueRead) {
            super.onSuccess(valueRead);
            progressDialog.dismiss();
            Toast.makeText(context, "Programa de fidelização cadastrado com sucesso", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Exception erro) {
            super.onError(erro);
            Toast.makeText(context, erro.getMessage(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    };

    /*
       EXIBIR SPINNER
    */
    private void showSpinner() {
        //Atualizando a lista
        List<String> tipoFidelizacao = new ArrayList<>();

        //Adicionando a categoria
        tipoFidelizacao.add("Selecione");
       //tipoFidelizacao.add(TipoFidelizacao.DINHEIRO.toString());
        tipoFidelizacao.add(TipoFidelizacao.UNIDADE.toString());

        //Populando o spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, tipoFidelizacao);
        spinner.setAdapter(adapter);
    }
}
