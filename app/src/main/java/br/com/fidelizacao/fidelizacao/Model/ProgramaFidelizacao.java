package br.com.fidelizacao.fidelizacao.Model;

import java.util.Date;

public class ProgramaFidelizacao {
    private Long programaFidelizacaoId;
    private Date tempoExpiracao;
    private Adm usuarioCadastro;
    private boolean status = true;
    private double qtdPremio;
    private TipoFidelizacao tipoFidelizacao;
    private Date dataCadastro = new Date();

    public Long getId() {
        return programaFidelizacaoId;
    }

    public void setId(Long id) {
        this.programaFidelizacaoId = id;
    }

    public Date getTempoExpiracao() {
        return tempoExpiracao;
    }

    public void setTempoExpiracao(Date tempoExpiracao) {
        this.tempoExpiracao = tempoExpiracao;
    }

    public Adm getUsuario_cadastro() {
        return usuarioCadastro;
    }

    public void setUsuario_cadastro(Adm usuario_cadastro) {
        this.usuarioCadastro = usuario_cadastro;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getQtd_premio() {
        return qtdPremio;
    }

    public void setQtd_premio(double qtd_premio) {
        this.qtdPremio = qtd_premio;
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
