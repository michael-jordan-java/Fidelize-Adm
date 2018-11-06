package br.com.fidelizacao.fidelizacao.Service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import br.com.fidelizacao.fidelizacao.Activity.SplashScreenActivity;
import br.com.fidelizacao.fidelizacao.Util.PushNotificationUtil;

/**
 * Created by Stefanini on 07/06/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //Verificando se contem mensagem
        if (remoteMessage.getNotification() != null) {
            PushNotificationUtil.sendNotification(this, SplashScreenActivity.class, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

}
