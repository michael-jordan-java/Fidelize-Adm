package br.com.fidelizacao.fidelizacao.Model;

import java.util.Date;

public class Cliente {

    private Long clienteId;

    private String cpf;

    private String nome;

    private String email;

    private String celular;

    private Date dataNascimento;

    private Date dataCadastro;

    private Date dataUltimoAcesso;

    private String tokenPushNotification;

    private boolean status;

    private boolean isPrimeiraCompra;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataUltimoAcesso() {
        return dataUltimoAcesso;
    }

    public void setDataUltimoAcesso(Date dataUltimoAcesso) {
        this.dataUltimoAcesso = dataUltimoAcesso;
    }

    public String getTokenPushNotification() {
        return tokenPushNotification;
    }

    public void setTokenPushNotification(String tokenPushNotification) {
        this.tokenPushNotification = tokenPushNotification;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isPrimeiraCompra() {
        return isPrimeiraCompra;
    }

    public void setPrimeiraCompra(boolean primeiraCompra) {
        isPrimeiraCompra = primeiraCompra;
    }
}

