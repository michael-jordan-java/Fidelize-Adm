package br.com.fidelizacao.fidelizacao.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import br.com.fidelizacao.R;
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
    private EditText etDataExpiracao, etQtdPremio;
    private Calendar calendar;
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

    }


    @Override
    protected void onStart() {
        super.onStart();

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
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

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
        String dataExpiracao = etDataExpiracao.getText().toString().trim();
        String qtdPremio = etQtdPremio.getText().toString().trim();
        ProgramaFidelizacao controleFidelidade = new ProgramaFidelizacao();


        if (dataExpiracao.isEmpty() || qtdPremio.isEmpty()) {
            Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        } else {
            // Verificando se a data de expiração é menor que a data atual
            Date tempoExpiracao = new Date(dataExpiracao);
            if (!tempoExpiracao.after(new Date())) {
                Toast.makeText(context, "O tempo de expiração não pode ser menor que a data atual", Toast.LENGTH_SHORT).show();
            } else {
                controleFidelidade.setTempoExpiracao(tempoExpiracao);
                controleFidelidade.setTipoFidelizacao(TipoFidelizacao.UNIDADE);
                controleFidelidade.setQtdPremio(Integer.parseInt(qtdPremio));
                controleFidelidade.setUsuarioCadastro(PrefsUtil.getLogin(context));
                controleFidelidade.setStatus(true);
                controleFidelidade.setDataCadastro(new Date());

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
            if (erro.getMessage().equals("Erro: 400")) {
                Toast.makeText(context, "Já possuí um programa de fidelização ativo cadastrado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, erro.getMessage(), Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }
    };

}
