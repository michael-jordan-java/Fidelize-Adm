package br.com.fidelizacao.fidelizacao.Model;

import java.util.Date;
import java.util.List;

public class ControleFidelidade {
    private Long id;
    private Date tempoExpiracao;
    private Adm usuario_cadastro;
    private boolean status = true;
    private double qtd_premio;
    private TipoFidelizacao tipoFidelizacao;
    private Date dataCadastro = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTempoExpiracao() {
        return tempoExpiracao;
    }

    public void setTempoExpiracao(Date tempoExpiracao) {
        this.tempoExpiracao = tempoExpiracao;
    }

    public Adm getUsuario_cadastro() {
        return usuario_cadastro;
    }

    public void setUsuario_cadastro(Adm usuario_cadastro) {
        this.usuario_cadastro = usuario_cadastro;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getQtd_premio() {
        return qtd_premio;
    }

    public void setQtd_premio(double qtd_premio) {
        this.qtd_premio = qtd_premio;
    }

    public TipoFidelizacao getTipoFidelizacao() {
        return tipoFidelizacao;
    }

    public void setTipoFidelizacao(TipoFidelizacao tipoFidelizacao) {
        this.tipoFidelizacao = tipoFidelizacao;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
