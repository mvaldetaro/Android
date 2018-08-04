package br.edu.infnet.at.salarioliquido;

/**
 * Created by Magno on 22/10/2017.
 */

public class Salario {
    private Double salarioLiquido;
    private Double totalDescontos;
    private Double percentualDescontos;

    public Salario(Double salarioLiquido, Double totalDescontos, Double percentualDescontos) {
        this.salarioLiquido = salarioLiquido;
        this.totalDescontos = totalDescontos;
        this.percentualDescontos = percentualDescontos;
    }

    public Double getSalarioLiquido() {
        return salarioLiquido;
    }

    public void setSalarioLiquido(Double salarioLiquido) {
        this.salarioLiquido = salarioLiquido;
    }

    public Double getTotalDescontos() {
        return totalDescontos;
    }

    public void setTotalDescontos(Double totalDescontos) {
        this.totalDescontos = totalDescontos;
    }

    public Double getPercentualDescontos() {
        return percentualDescontos;
    }

    public void setPercentualDescontos(Double percentualDescontos) {
        this.percentualDescontos = percentualDescontos;
    }
}
