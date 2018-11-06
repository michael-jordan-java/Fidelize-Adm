package br.com.fidelizacao.fidelizacao.Util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import br.com.fidelizacao.R;

/**
 * Created by Stefanini on 07/06/2017.
 */

public class PushNotificationUtil {
    public static void sendNotification(Context context, Class classe, String titulo, String mensagem) {

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context);

        //Criando uma intenção
        Intent intent = new Intent(context, classe);

        //setando a flag
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        notifyBuilder.setContentIntent(pendingIntent);

        //Definido o icon que acompanhará na notificação
        notifyBuilder.setSmallIcon(R.mipmap.ic_launcher);

        //Definindo o titulo
        notifyBuilder.setContentTitle(titulo);

        //Definindo o texto da mensagem
        notifyBuilder.setContentText(mensagem);

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(1000);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(R.mipmap.ic_launcher, notifyBuilder.build());

        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
