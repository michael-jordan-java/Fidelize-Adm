package br.com.fidelizacao.fidelizacao.Service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Stefanini on 07/06/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    /*
        Chamado se o Token InstanceId for atualizado. Isso pode ocorrer se a segurança do
        token anterior ter sido comprometida. Observe que isso é chamado quando o token
        do InstanceId é inicialmente gerado, então é aqui que você recupera o token;
     */

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        //Obtendo o token InstanceId atualizado
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("fidelizacao");
    }
}
