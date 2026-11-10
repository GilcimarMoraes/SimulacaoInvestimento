package caixaverso.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Investimento {

    private Long id;
    private Long clienteId;
    private String tipo;
    private BigDecimal valor;
    private BigDecimal rentabilidade;
    private LocalDate data;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }

    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getTipo() { return tipo;}

    public void setTipo(String tipo) { this.tipo = tipo; }

    public BigDecimal getValor() { return valor;}

    public void setValor(BigDecimal valor) { this.valor = valor; }

    public BigDecimal getRentabilidade() { return rentabilidade;}

    public void setRentabilidade(BigDecimal rentabilidade) { this.rentabilidade = rentabilidade; }

    public LocalDate getData() { return data; }

    public void setData(LocalDate data) { this.data = data;}

    public Investimento(Long id, Long clienteId, String tipo, BigDecimal valor, BigDecimal rentabilidade,
                        LocalDate data) {
        this.id = id;
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.valor = valor;
        this.rentabilidade = rentabilidade;
        this.data = data;
    }

    public Investimento() {
    }
}
