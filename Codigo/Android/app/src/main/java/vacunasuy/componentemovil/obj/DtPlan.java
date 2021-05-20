package vacunasuy.componentemovil.obj;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DtPlan  {
    private Integer id;
    private Integer edadMinima;
    private Integer edadMaxima;
    private Date fechaInicio;
    private Date fechaFin;
    private List<DtSectorLaboral> sectores;
    private DtVacuna vacunas;

    public DtPlan() {
    }

    public DtPlan(Integer id, Integer edadMinima, Integer edadMaxima, Date fechaInicio, Date fechaFin, List<DtSectorLaboral> sectores, DtVacuna vacunas) {
        this.id = id;
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.sectores = sectores;
        this.vacunas = vacunas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(Integer edadMinima) {
        this.edadMinima = edadMinima;
    }

    public Integer getEdadMaxima() {
        return edadMaxima;
    }

    public void setEdadMaxima(Integer edadMaxima) {
        this.edadMaxima = edadMaxima;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<DtSectorLaboral> getSectores() {
        return sectores;
    }

    public void setSectores(List<DtSectorLaboral> sectores) {
        this.sectores = sectores;
    }

    public DtVacuna getVacuna() {
        return vacunas;
    }

    public void setVacuna(DtVacuna vacunas) {
        this.vacunas = vacunas;
    }
}
