package br.com.fidelizacao.fidelizacao.Model;

import java.util.Date;

public class Fidelizacao {
    private Long fidelizacaoId;
    private Cliente cliente;
    private ProgramaFidelizacao programaFidelizacao;
    private Date dataCompra;
    private boolean status;

    public Long getFidelizacaoId() {
        return fidelizacaoId;
    }

    public void setFidelizacaoId(Long fidelizacaoId) {
        this.fidelizacaoId = fidelizacaoId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ProgramaFidelizacao getProgramaFidelizacao() {
        return programaFidelizacao;
    }

    public void setProgramaFidelizacao(ProgramaFidelizacao programaFidelizacao) {
        this.programaFidelizacao = programaFidelizacao;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
