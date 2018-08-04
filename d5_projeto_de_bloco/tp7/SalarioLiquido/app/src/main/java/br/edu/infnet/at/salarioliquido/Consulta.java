package br.edu.infnet.at.salarioliquido;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Magno on 22/10/2017.
 */

public class Consulta {
    private String dataConsulta;
    private String horarioConsulta;
    private Integer qtdDependentes;
    private Double pensaoAlimenticia;
    private Double planoSaude;
    private Double outrosDescontos;
    private Double salarioLiquido;
    private Double totalDescontos;
    private Double porcentagemDescontos;

    public Consulta(Integer qtdDependentes, Double pensaoAlimenticia, Double planoSaude, Double outrosDescontos, Double salarioLiquido, Double totalDescontos, Double porcentagemDescontos) {

        DateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        this.dataConsulta = dataFormat.format(date);
        this.horarioConsulta = horaFormat.format(date);
        this.qtdDependentes = qtdDependentes;
        this.pensaoAlimenticia = pensaoAlimenticia;
        this.planoSaude = planoSaude;
        this.outrosDescontos = outrosDescontos;
        this.salarioLiquido = salarioLiquido;
        this.totalDescontos = totalDescontos;
        this.porcentagemDescontos = porcentagemDescontos;
    }

    public String getDataConsulta() {
        return dataConsulta;
    }

    public String getHorarioConsulta() {
        return horarioConsulta;
    }

    public Integer getQtdDependentes() {
        return qtdDependentes;
    }

    public void setQtdDependentes(Integer qtdDependentes) {
        this.qtdDependentes = qtdDependentes;
    }

    public Double getPensaoAlimenticia() {
        return pensaoAlimenticia;
    }

    public void setPensãoAlimentícia(Double pensãoAlimentícia) {
        this.pensaoAlimenticia = pensaoAlimenticia;
    }

    public Double getPlanoSaude() {
        return planoSaude;
    }

    public void setPlanoSaude(Double planoSaude) {
        this.planoSaude = planoSaude;
    }

    public Double getOutrosDescontos() {
        return outrosDescontos;
    }

    public void setOutrosDescontos(Double outrosDescontos) {
        this.outrosDescontos = outrosDescontos;
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

    public Double getPorcentagemDescontos() {
        return porcentagemDescontos;
    }

    public void setPorcentagemDescontos(Double porcentagemDescontos) {
        this.porcentagemDescontos = porcentagemDescontos;
    }
}
