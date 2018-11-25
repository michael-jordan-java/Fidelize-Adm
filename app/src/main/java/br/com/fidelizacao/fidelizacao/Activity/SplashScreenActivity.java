package br.com.fidelizacao.fidelizacao.Activity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

import br.com.fidelizacao.R;
import br.com.fidelizacao.fidelizacao.RestAdress.RestAddress;
import br.com.fidelizacao.fidelizacao.Task.HandlerTask;
import br.com.fidelizacao.fidelizacao.Task.TaskRest;
import br.com.fidelizacao.fidelizacao.Util.JsonParser;
import br.com.fidelizacao.fidelizacao.Util.PrefsUtil;

public class SplashScreenActivity extends AppCompatActivity {
    private static Context context;
    private long backPressedTime = 0;
    final long TEMPO_TIMER_GANHADORES = (10000); //executa a cada 10 segundos

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        context = this;

        // Verifica se o alarme está ativo
        boolean alarmeAtivoFidelizacao = (PendingIntent.getBroadcast(context, 0, new Intent("VERIFICAR_PROGRAMA_FIDELIDADE"), PendingIntent.FLAG_NO_CREATE) == null);
        boolean alarmeAtivoGanhadores = (PendingIntent.getBroadcast(context, 0, new Intent("VERIFICAR_GANHADORES"), PendingIntent.FLAG_NO_CREATE) == null);

        startAnimations();

        if (alarmeAtivoFidelizacao) {
            Log.e("logs", "Alarme fidelizacao ativo");
            //Definir a hora de início
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 10);

            Intent tarefaIntent = new Intent("VERIFICAR_PROGRAMA_FIDELIDADE");
            PendingIntent tarefaPendingIntent = PendingIntent.getBroadcast(context, 0, tarefaIntent, 0);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            //Definir o tempo que o alarme será executado (1 vez por dia) 86400000
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    86400000, tarefaPendingIntent);
        }

        if (alarmeAtivoGanhadores) {
            Log.e("logs", "Alarme ganhadores ativo");
            //Definir a hora de início
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 10);

            Intent tarefaIntent = new Intent("VERIFICAR_GANHADORES");
            PendingIntent tarefaPendingIntent = PendingIntent.getBroadcast(context, 0, tarefaIntent, 0);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            //Definir o tempo que o alarme será executado (5 min)
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    TEMPO_TIMER_GANHADORES, tarefaPendingIntent);
        }

        new TaskRest(TaskRest.RequestMethod.POST, handlerTaskAtualizaTokenPushNotification).execute(RestAddress.ATUALIZA_TOKEN_PUSH_NOTIFICATION + 1, new JsonParser<>(String.class).fromObject(PrefsUtil.getTokenPushNotification(context)));
    }

    private HandlerTask handlerTaskAtualizaTokenPushNotification = new HandlerTask() {
        @Override
        public void onPreHandle() {

        }

        @Override
        public void onSuccess(String valueRead) {

        }

        @Override
        public void onError(Exception erro) {
            erro.printStackTrace();
            Toast.makeText(SplashScreenActivity.this, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void startAnimations() {
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animation.reset();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(context, android.R.anim.slide_in_left, android.R.anim.fade_out);
                ActivityCompat.startActivity(context, new Intent(context, LoginActivity.class), activityOptionsCompat.toBundle());
                SplashScreenActivity.this.finish();
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {
        long time = System.currentTimeMillis();
        if ((time - backPressedTime) > 2000) {
            backPressedTime = time;
            Toast.makeText(context, getString(R.string.backpressed), Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
