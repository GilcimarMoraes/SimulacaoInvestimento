package caixaverso.domain.entity;

import java.math.BigDecimal;
import java.time.Instant;

public class Simulacao {

    private Long id;

    private Long clienteId;

    private String produto;

    private BigDecimal valorInvestido;

    private BigDecimal valorFinal;

    private BigDecimal rentabilidadeEfetiva;

    private int prazoMeses;

    private Instant dataSimulacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clientId) {
        this.clienteId = clientId;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public BigDecimal getValorInvestido() {
        return valorInvestido;
    }

    public void setValorInvestido(BigDecimal valorInvestido) {
        this.valorInvestido = valorInvestido;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    public BigDecimal getRentabilidadeEfetiva() { return rentabilidadeEfetiva; }

    public void setRentabilidadeEfetiva(BigDecimal rentabilidadeEfetiva) {
        this.rentabilidadeEfetiva = rentabilidadeEfetiva; }

    public int getPrazoMeses() {
        return prazoMeses;
    }

    public void setPrazoMeses(int prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

    public Instant getDataSimulacao() {
        return dataSimulacao;
    }

    public void setDataSimulacao(Instant dataSimulacao) {
        this.dataSimulacao = dataSimulacao;
    }

    public Simulacao(Long id, Long clienteId, String produto, BigDecimal valorInvestido, BigDecimal valorFinal,
                        BigDecimal rentabilidadeEfetiva, int prazoMeses, Instant dataSimulacao) {
        this.id = id;
        this.clienteId = clienteId;
        this.produto = produto;
        this.valorInvestido = valorInvestido;
        this.valorFinal = valorFinal;
        this.rentabilidadeEfetiva = rentabilidadeEfetiva;
        this.prazoMeses = prazoMeses;
        this.dataSimulacao = dataSimulacao;
    }

    public Simulacao() {
    }
}
