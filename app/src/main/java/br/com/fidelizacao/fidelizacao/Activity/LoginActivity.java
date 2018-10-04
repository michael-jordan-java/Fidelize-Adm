package br.com.fidelizacao.fidelizacao.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.fidelizacao.R;
import br.com.fidelizacao.fidelizacao.Model.Adm;
import br.com.fidelizacao.fidelizacao.RestAdress.RestAddress;
import br.com.fidelizacao.fidelizacao.Task.HandlerTask;
import br.com.fidelizacao.fidelizacao.Task.HandlerTaskAdapter;
import br.com.fidelizacao.fidelizacao.Task.TaskRest;
import br.com.fidelizacao.fidelizacao.Util.JsonParser;
import br.com.fidelizacao.fidelizacao.Util.PrefsUtil;

public class LoginActivity extends AppCompatActivity {
    private EditText txtEmail, txtSenha;
    private Context context;
    private ProgressDialog progressDialog;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        progressDialog = new ProgressDialog(context);

        // Pegando a instancia do EditText
        txtEmail = findViewById(R.id.etEmail);
        txtSenha = findViewById(R.id.etSenha);
    }

    public void loginClick(View v) {
        if (txtEmail.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Preencha o email", Toast.LENGTH_LONG).show();
        } else if (txtSenha.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Preencha o email", Toast.LENGTH_LONG).show();
        } else {
            Adm adm = new Adm();
            adm.setEmail(txtEmail.getText().toString().trim());
            adm.setSenha(txtSenha.getText().toString().trim());

            // Faz parse do objeto para json
            JsonParser<Adm> parser = new JsonParser<>(Adm.class);

            new TaskRest(TaskRest.RequestMethod.POST, handlerLogin).execute(RestAddress.LOGIN, parser.fromObject(adm));
        }
    }

    private HandlerTask handlerLogin = new HandlerTaskAdapter() {
        @Override
        public void onPreHandle() {
            progressDialog.setMessage("Validando informações...");
            progressDialog.show();
        }

        @Override
        public void onSuccess(String valueRead) {
            // Salvando login do usuario nas preferencias
            PrefsUtil.salvarLogin(context, valueRead);

            startActivity(new Intent(context, MainActivity.class));
            finish();
        }

        @Override
        public void onError(Exception erro) {
            progressDialog.dismiss();
            if (erro.getMessage().trim().equals("Erro: 401")) {
                Toast.makeText(getBaseContext(), "Email/Senha Inválidos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), erro.getMessage(), Toast.LENGTH_SHORT).show();
                erro.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        long time = System.currentTimeMillis();
        if (time - backPressedTime > 2000) {    // 2 secs
            backPressedTime = time;
            Toast.makeText(this, "Pressione novamente para sair", Toast.LENGTH_SHORT).show();
        } else {
            // clean up
            super.onBackPressed();
        }
    }
}
