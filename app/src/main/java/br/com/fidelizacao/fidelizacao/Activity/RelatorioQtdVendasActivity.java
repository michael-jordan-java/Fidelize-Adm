package br.com.fidelizacao.fidelizacao.Activity;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.fidelizacao.R;
import br.com.fidelizacao.fidelizacao.Model.Fidelizacao;
import br.com.fidelizacao.fidelizacao.RestAdress.RestAddress;
import br.com.fidelizacao.fidelizacao.Task.HandlerTask;
import br.com.fidelizacao.fidelizacao.Task.TaskRest;
import br.com.fidelizacao.fidelizacao.Util.JsonParser;
import br.com.fidelizacao.fidelizacao.Util.ReturnRelatorioQtdVendasUtil;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class RelatorioQtdVendasActivity extends AppCompatActivity {
    private Context context;
    private Toolbar toolbar;
    private List<ReturnRelatorioQtdVendasUtil> dadosRelatorios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_qtd_vendas);

        context = this;

        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Relatórios");
        }

        new TaskRest(TaskRest.RequestMethod.GET, handlerQtdVendas).execute(RestAddress.RELATORIO_QTD_VENDAS_ESTABELECIMENTO);

    }

    private HandlerTask handlerQtdVendas = new HandlerTask() {
        @Override
        public void onPreHandle() {

        }

        @Override
        public void onSuccess(String valueRead) {
            Log.e("logs", valueRead);

            dadosRelatorios = new JsonParser<>(ReturnRelatorioQtdVendasUtil.class).toList(valueRead, ReturnRelatorioQtdVendasUtil[].class);
            PieChartView pieChartView = findViewById(R.id.chart);

            List<SliceValue> pieData = new ArrayList<>();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            List<Integer> colors = new ArrayList<>();
            colors.add(Color.BLUE);
            colors.add(Color.RED);
            colors.add(Color.MAGENTA);
            colors.add(Color.YELLOW);
            colors.add(Color.GREEN);
            colors.add(Color.CYAN);
            colors.add(Color.GRAY);


            for (int i = 0; i < dadosRelatorios.size(); i++) {
                StringBuilder builder = new StringBuilder();
                builder.append("Pontos: " + dadosRelatorios.get(i).getQtdVendas() + " - Data: " + format.format(dadosRelatorios.get(i).getDataCompra()));

                pieData.add(new SliceValue(dadosRelatorios.get(i).getQtdVendas(), colors.get(i)).setLabel(builder.toString()));
            }

            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(14);
            pieChartData.setHasCenterCircle(true).setCenterText1("Fidelizou").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));

            pieChartView.setPieChartData(pieChartData);

        }

        @Override
        public void onError(Exception erro) {
            erro.printStackTrace();
            Toast.makeText(context, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
