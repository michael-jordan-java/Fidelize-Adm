package br.com.fidelizacao.fidelizacao.RestAdress;

/**
 * Created by Stefanini on 25/04/2017.
 */

public class RestAddress {
    //Endereco base da API
    public static final String URL = "http://192.168.0.100:8080/Fidelizacao";

    //Endereços de recursos da API
    public static final String LOGIN = URL + "/rest/adm/logar";
    public static final String CADASTRAR_FIDELIDADE = URL + "rest/controleFidelidade";
}
