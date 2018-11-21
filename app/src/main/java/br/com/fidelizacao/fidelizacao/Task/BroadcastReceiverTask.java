package br.com.fidelizacao.fidelizacao.Task;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.fidelizacao.R;
import br.com.fidelizacao.fidelizacao.Activity.GanhadoresActivity;
import br.com.fidelizacao.fidelizacao.Activity.SplashScreenActivity;
import br.com.fidelizacao.fidelizacao.Model.Cliente;
import br.com.fidelizacao.fidelizacao.Model.ProgramaFidelizacao;
import br.com.fidelizacao.fidelizacao.RestAdress.RestAddress;
import br.com.fidelizacao.fidelizacao.Util.JsonParser;
import br.com.fidelizacao.fidelizacao.Util.PrefsUtil;

import static android.content.Context.NOTIFICATION_SERVICE;

public class BroadcastReceiverTask extends BroadcastReceiver {
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        if (intent.getAction().equals("VERIFICAR_PROGRAMA_FIDELIDADE")) {
            verificarProgramaFidelidade();
        } else if (intent.getAction().equals("VERIFICAR_GANHADORES")) {
            verificarGanhadores();
        }

    }

    public void verificarProgramaFidelidade() {
        new TaskRest(TaskRest.RequestMethod.GET, handlerGetProgramaFidelidade).execute(RestAddress.BUSCAR_PROGRAMA_FIDELIZACAO);
    }

    public void verificarGanhadores() {
        new TaskRest(TaskRest.RequestMethod.GET, handlerGanhadores).execute(RestAddress.BUSCAR_GANHADORES);
    }


    private HandlerTask handlerGetProgramaFidelidade = new HandlerTaskAdapter() {
        @Override
        public void onPreHandle() {
            super.onPreHandle();
        }

        @Override
        public void onSuccess(String valueRead) {
            super.onSuccess(valueRead);

            if (valueRead.isEmpty()) {
                gerarNotificacao(context, new Intent(context, SplashScreenActivity.class), "Novo aviso", "Nenhum programa de fidelização cadastrado!", "por favor cadastre um novo programa de fidelização");
            } else {
                ProgramaFidelizacao programaFidelizacao = new JsonParser<>(ProgramaFidelizacao.class).toObject(valueRead);

                // usa calendar para subtrair data
                Calendar calendarData = Calendar.getInstance();
                calendarData.setTime(programaFidelizacao.getTempoExpiracao());
                calendarData.add(Calendar.DAY_OF_MONTH, -2);
                Date dataAux = calendarData.getTime();

                Date dataAtual = new Date();

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                if (dataAtual.getTime() == programaFidelizacao.getTempoExpiracao().getTime()) {
                    gerarNotificacao(context, new Intent(context, SplashScreenActivity.class), "Novo aviso", "Programa de Fidelização", "O programa de fidelização expira hoje as 21:00 hr");
                } else if (dataAtual.getTime() >= dataAux.getTime() && dataAtual.getTime() < programaFidelizacao.getTempoExpiracao().getTime()) {
                    gerarNotificacao(context, new Intent(context, SplashScreenActivity.class), "Novo aviso", "Programa de Fidelização", "O tempo do programa de fidelização expira no dia " + format.format(programaFidelizacao.getTempoExpiracao()));
                }
            }
        }

        @Override
        public void onError(Exception erro) {
            super.onError(erro);
            erro.printStackTrace();
            Toast.makeText(context, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private HandlerTask handlerGanhadores = new HandlerTask() {
        @Override
        public void onPreHandle() {

        }

        @Override
        public void onSuccess(String valueRead) {
            if (!valueRead.isEmpty()) {
                Log.e("logs", valueRead);
                gerarNotificacaoGanhadores(context, new Intent(context, SplashScreenActivity.class), new JsonParser<>(Cliente.class).toList(valueRead, Cliente[].class));
            }else{
                gerarNotificacao(context,new Intent(context, GanhadoresActivity.class), "Novo aviso", "Nenhum ganhador por enquanto :(", "");
            }
        }

        @Override
        public void onError(Exception erro) {

        }
    };

    public void gerarNotificacao(Context context, Intent intent, CharSequence ticker, CharSequence titulo, CharSequence descricao) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, SplashScreenActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(ticker);
        builder.setContentTitle(titulo);
        builder.setContentText(descricao);
        builder.setSmallIcon(R.drawable.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo));
        builder.setContentIntent(pendingIntent);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(descricao));

        /*NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        String[] descs = new String[]{"Descrição 01", "Descrição 02", "Descrição 03", "Descrição 04"};
        for (int i = 0; i < descs.length ; i++) {
            style.addLine(descs[i]);
        }

        builder.setStyle(style);
*/

        Notification notification = builder.build();
        notification.vibrate = new long[]{150, 300, 150, 600};
        notification.flags = Notification.FLAG_AUTO_CANCEL;


        //Enviar notificacao
        notificationManager.notify(R.drawable.logo, notification);

        try {
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();

        } catch (Exception e) {

        }

    }

    public void gerarNotificacaoGanhadores(Context context, Intent intent, List<Cliente> clientes) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, SplashScreenActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker("Novos ganhadores");
        builder.setContentTitle("Ganhadores da promoção");
        builder.setSmallIcon(R.drawable.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo));
        builder.setContentIntent(pendingIntent);

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();

        for (int i = 0; i < clientes.size(); i++) {
            style.addLine(clientes.get(i).getNome());
        }
        builder.setStyle(style);

        Notification notification = builder.build();
        notification.vibrate = new long[]{150, 300, 150, 600};
        notification.flags = Notification.FLAG_AUTO_CANCEL;


        //Enviar notificacao
        notificationManager.notify(R.drawable.logo, notification);

        try {
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();

        } catch (Exception e) {

        }

    }
}
