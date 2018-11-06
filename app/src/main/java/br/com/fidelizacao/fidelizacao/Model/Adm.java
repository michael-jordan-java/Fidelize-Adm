package br.com.fidelizacao.fidelizacao.Model;

import java.util.Date;

public class Adm {
    private Long admId;

    private String nomeUsuario;

    private String senha;

    private String email;

    private String celular;

    private Date dataExpiracao;

    private boolean primeiroLogin;

    private boolean isMaster;

    private boolean status;

    private Date dataCadastro;

    public Long getId() {
        return admId;
    }

    public void setId(Long id) {
        this.admId = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public boolean isPrimeiro_login() {
        return primeiroLogin;
    }

    public void setPrimeiro_login(boolean primeiro_login) {
        this.primeiroLogin = primeiro_login;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean isMaster) {
        this.isMaster = isMaster;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }


}
