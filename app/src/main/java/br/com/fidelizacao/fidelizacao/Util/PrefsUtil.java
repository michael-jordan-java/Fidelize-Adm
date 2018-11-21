package br.com.fidelizacao.fidelizacao.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import br.com.fidelizacao.fidelizacao.Model.Adm;
import br.com.fidelizacao.fidelizacao.Model.ProgramaFidelizacao;

/**
 * Created by Stefanini on 25/04/2017.
 */

public class PrefsUtil {
    public static void salvarLogin(Context context, String adm) {
        //Pegando a preferencia default
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        //Editando a preferencia default
        SharedPreferences.Editor editor = preferences.edit();
        //Adicionando as informações do login na preferencia
        editor.putString("login", adm);
        editor.commit();
    }

    public static Adm getLogin(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (preferences.getString("login", "").isEmpty()) {
            return null;
        } else {
            String preferencesStringAdm = preferences.getString("login", "");

            if(preferencesStringAdm.isEmpty()) {
                return null;
            }else{
                Adm adm = new JsonParser<Adm>(Adm.class).toObject(preferencesStringAdm);
                return adm;
            }

        }
    }

    public static void salvarProgramaFidelizacao(Context context, String programaFidelizacao) {
        //Pegando a preferencia default
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        //Editando a preferencia default
        SharedPreferences.Editor editor = preferences.edit();
        //Adicionando as informações do login na preferencia
        editor.putString("programaFidelizacao", programaFidelizacao);
        editor.commit();
    }

    public static ProgramaFidelizacao getProgramaFidelizacao(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (preferences.getString("programaFidelizacao", "").isEmpty()) {
            return null;
        } else {
            String preferencesStringAdm = preferences.getString("programaFidelizacao", "");

            if(preferencesStringAdm.isEmpty()) {
                return null;
            }else{
                ProgramaFidelizacao programaFidelizacao = new JsonParser<ProgramaFidelizacao>(ProgramaFidelizacao.class).toObject(preferencesStringAdm);
                return programaFidelizacao;
            }

        }
    }

    public static void atualizaTokenPushNotification(Context context, String token) {
        //Pegando a preferencia default
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        //Editando a preferencia default
        SharedPreferences.Editor editor = preferences.edit();
        //Adicionando as informações do login na preferencia
        editor.putString("tokenUsuarioPushNotification", token);
        editor.commit();
    }

    public static String getTokenPushNotification(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (preferences.getString("tokenUsuarioPushNotification", "").isEmpty()) {
            return null;
        } else {
            String preferencesStringTokenAdm = preferences.getString("tokenUsuarioPushNotification", "");

            if(preferencesStringTokenAdm.isEmpty()) {
                return null;
            }else{
                return preferencesStringTokenAdm;
            }

        }
    }
}
