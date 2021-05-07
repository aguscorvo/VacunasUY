package vacunasuy.componentemovil.obj;

public class DtMessage {
    private Double enfermedad;
    private Double vacuna;
    private Double plan;
    private Double agenda;
    private Double proximas;
    private Double vacunatorio;

    public DtMessage(Double enfermedad, Double vacuna, Double plan, Double agenda, Double proximas, Double vacunatorio) {
        this.enfermedad = enfermedad;
        this.vacuna = vacuna;
        this.plan = plan;
        this.agenda = agenda;
        this.proximas = proximas;
        this.vacunatorio = vacunatorio;
    }

    public Double getEnfermedad() {
        return enfermedad;
    }

    public Double getVacuna() {
        return vacuna;
    }

    public Double getPlan() {
        return plan;
    }

    public Double getAgenda() {
        return agenda;
    }

    public Double getProximas() {
        return proximas;
    }

    public Double getVacunatorio() {
        return vacunatorio;
    }
}
