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

import static android.content.Context.NOTIFICATION_SERVICE;

public class BroadcastReceiverGanhadoresTask extends BroadcastReceiver {
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (intent.getAction().equals("VERIFICAR_GANHADORES")) {
            verificarGanhadores();
        }

    }

    public void verificarGanhadores() {
        new TaskRest(TaskRest.RequestMethod.GET, handlerGanhadores).execute(RestAddress.BUSCAR_GANHADORES);
    }


    private HandlerTask handlerGanhadores = new HandlerTask() {
        @Override
        public void onPreHandle() {

        }

        @Override
        public void onSuccess(String valueRead) {
            List<Cliente> clientes = new JsonParser<>(Cliente.class).toList(valueRead, Cliente[].class);

            if (!clientes.isEmpty()) {
                gerarNotificacaoGanhadores(context, new Intent(context, SplashScreenActivity.class), clientes);
                for (Cliente cliente : clientes) {
                    new TaskRest(TaskRest.RequestMethod.POST, handlerTaskEnviarAviso).execute(RestAddress.ENVIAR_AVISOS + cliente.getClienteId(), "Voce ganhou o premio da fidelizacao");
                }
            }
        }

        @Override
        public void onError(Exception erro) {

        }
    };

    private HandlerTask handlerTaskEnviarAviso = new HandlerTask() {
        @Override
        public void onPreHandle() {

        }

        @Override
        public void onSuccess(String valueRead) {

        }

        @Override
        public void onError(Exception erro) {
            erro.printStackTrace();
            Toast.makeText(context, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

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
