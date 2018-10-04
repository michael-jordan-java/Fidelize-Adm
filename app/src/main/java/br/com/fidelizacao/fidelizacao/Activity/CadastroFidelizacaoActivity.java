package br.com.fidelizacao.fidelizacao.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.fidelizacao.R;
import br.com.fidelizacao.fidelizacao.Model.TipoFidelizacao;
import br.com.fidelizacao.fidelizacao.Task.HandlerTask;
import br.com.fidelizacao.fidelizacao.Task.HandlerTaskAdapter;

public class CadastroFidelizacaoActivity extends AppCompatActivity {
    private Context context;
    private TextInputEditText etDataExpiracao, etQtdPremio;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private int ano, mes, dia;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_fidelizacao);

        context = this;

        etDataExpiracao = findViewById(R.id.etDataExpiracao);
        etQtdPremio = findViewById(R.id.etQtdPremio);

        spinner = findViewById(R.id.spinnerTipoFidelizacao);
        spinner.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    protected void onStart() {
        super.onStart();

        showSpinner();

        etDataExpiracao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v.getId() == R.id.etDataExpiracao) {

                    calendar = Calendar.getInstance();
                    ano = calendar.get(Calendar.YEAR);
                    mes = calendar.get(Calendar.MONTH);
                    dia = calendar.get(Calendar.DAY_OF_MONTH);

                    datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            etDataExpiracao.setText(dia + "/" + mes + "/" + ano);
                            Toast.makeText(context, "Dia: " + dayOfMonth + " Mês: " + month + " ano: " + year, Toast.LENGTH_SHORT).show();
                        }
                    }, ano, mes, dia);


                    datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    etDataExpiracao.clearFocus(); //Focus must be cleared so the value change listener is called
                                    etDataExpiracao.setText("");
                                    datePickerDialog.dismiss();
                                }
                            });

                    datePickerDialog.show();
                }
            }
        });
    }

    public void salvarOnClick(View view){
        String spinnerItem = spinner.getSelectedItem().toString().trim();
        String dataExpiracao = etDataExpiracao.getText().toString().trim();
        String qtdPremio = etQtdPremio.getText().toString().trim();

        if(spinnerItem.isEmpty() || dataExpiracao.isEmpty() || qtdPremio.isEmpty()){
            Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }else{

        }
    }

    private HandlerTask handlerSalvarFidelidade = new HandlerTaskAdapter() {
        @Override
        public void onPreHandle() {
            super.onPreHandle();
        }

        @Override
        public void onSuccess(String valueRead) {
            super.onSuccess(valueRead);
        }

        @Override
        public void onError(Exception erro) {
            super.onError(erro);
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
        tipoFidelizacao.add(TipoFidelizacao.DINHEIRO.toString());
        tipoFidelizacao.add(TipoFidelizacao.UNIDADE.toString());

        //Populando o spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, tipoFidelizacao);
        spinner.setAdapter(adapter);
    }
}
