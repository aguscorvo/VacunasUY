package vacunasuy.componentemovil.obj;

import java.util.Date;

public class DtAgenda {
    Integer id;
    Date fecha;
    DtVacunatorio vacunatorio;
    DtVacuna vacuna;


    public DtAgenda(Integer id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public DtAgenda(Integer id, Date fecha, DtVacunatorio vacunatorio, DtVacuna vacuna) {
        this.id = id;
        this.fecha = fecha;
        this.vacunatorio = vacunatorio;
        this.vacuna = vacuna;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public DtVacunatorio getVacunatorio() {
        return vacunatorio;
    }

    public void setVacunatorio(DtVacunatorio vacunatorio) {
        this.vacunatorio = vacunatorio;
    }

    public DtVacuna getVacuna() {
        return vacuna;
    }

    public void setVacuna(DtVacuna vacuna) {
        this.vacuna = vacuna;
    }
}
