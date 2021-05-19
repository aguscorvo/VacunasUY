package vacunasuy.componentemovil.obj;

public class DtVacuna {
    private Integer id;
    private String nombre;
    private Integer cant_dosis;
    private Integer periodo;
    private Integer inmunidad;
    private DtEnfermedad enfermedad;

    public DtVacuna() {
    }

    public DtVacuna(Integer id, String nombre, Integer cant_dosis, Integer periodo, Integer inmunidad, DtEnfermedad enfermedad) {
        this.id = id;
        this.nombre = nombre;
        this.cant_dosis = cant_dosis;
        this.periodo = periodo;
        this.inmunidad = inmunidad;
        this.enfermedad = enfermedad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCant_dosis() {
        return cant_dosis;
    }

    public void setCant_dosis(Integer cant_dosis) {
        this.cant_dosis = cant_dosis;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Integer getInmunidad() {
        return inmunidad;
    }

    public void setInmunidad(Integer inmunidad) {
        this.inmunidad = inmunidad;
    }

    public DtEnfermedad getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(DtEnfermedad enfermedad) {
        this.enfermedad = enfermedad;
    }
}
