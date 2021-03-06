package br.com.fidelizacao.fidelizacao.RestAdress;

/**
 * Created by Stefanini on 25/04/2017.
 */

public class RestAddress {
    //Endereco base da API
    public static final String URL = "http://192.168.1.244:8080/Fidelizacao";

    //Endereços de recursos da API
    public static final String LOGIN = URL + "/rest/adm/logar";
    public static final String CADASTRAR_FIDELIDADE = URL + "/rest/programaFidelizacao";
    public static final String BUSCAR_QTD_FIDELIZACAO = URL + "/rest/fidelizacao/qtdCompra/cliente/";
    public static final String BUSCAR_ANIVERSARIANTES = URL + "/rest/adm/cliente/aniversariantes";
    public static final String ENVIAR_AVISOS = URL + "/rest/adm/enviarAviso/";
    public static final String BUSCAR_PROGRAMA_FIDELIZACAO = URL + "/rest/programaFidelizacao/buscarProgramaFidelizacao";
    public static final String BUSCAR_CLIENTES = URL + "/rest/cliente/";
    public static final String ATUALIZA_TOKEN_PUSH_NOTIFICATION = URL + "/rest/adm/token/";
    public static final String BUSCAR_PROXIMOS_GANHADORES = URL + "/rest/cliente/proximosGanhadores";
    public static final String BUSCAR_GANHADORES = URL + "/rest/fidelizacao/ganhadores";
    public static final String RELATORIO_QTD_VENDAS_ESTABELECIMENTO = URL + "/rest/fidelizacao/relatorio/qtdVendasEstabelecimento";


}
