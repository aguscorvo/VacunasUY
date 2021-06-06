package vacunasuy.componentemovil.obj;

import java.util.Date;

public class DtDosis {
    Integer dosis;
    Integer cant_dosis;
    Date fecha;
    String vacuna;

    public DtDosis() {
    }

    public DtDosis(Integer dosis, Integer cant_dosis, Date fecha, String vacuna) {
        this.dosis = dosis;
        this.cant_dosis = cant_dosis;
        this.fecha = fecha;
        this.vacuna = vacuna;
    }

    public Integer getDosis() {
        return dosis;
    }

    public void setDosis(Integer dosis) {
        this.dosis = dosis;
    }

    public Integer getCant_dosis() {
        return cant_dosis;
    }

    public void setCant_dosis(Integer cant_dosis) {
        this.cant_dosis = cant_dosis;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getVacuna() {
        return vacuna;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
    }
}
