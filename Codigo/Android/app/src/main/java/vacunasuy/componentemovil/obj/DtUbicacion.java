package vacunasuy.componentemovil.obj;

public class DtUbicacion {
    Integer id_departamento;
    String nombre_departamento;
    Integer id_localidad;
    String nombre_localidad;
    String direccion;

    public DtUbicacion() {
    }

    public DtUbicacion(Integer id_departamento, String nombre_departamento, Integer id_localidad, String nombre_localidad, String direccion) {
        this.id_departamento = id_departamento;
        this.nombre_departamento = nombre_departamento;
        this.id_localidad = id_localidad;
        this.nombre_localidad = nombre_localidad;
        this.direccion = direccion;
    }

    public Integer getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(Integer id_departamento) {
        this.id_departamento = id_departamento;
    }

    public String getNombre_departamento() {
        return nombre_departamento;
    }

    public void setNombre_departamento(String nombre_departamento) {
        this.nombre_departamento = nombre_departamento;
    }

    public Integer getId_localidad() {
        return id_localidad;
    }

    public void setId_localidad(Integer id_localidad) {
        this.id_localidad = id_localidad;
    }

    public String getNombre_localidad() {
        return nombre_localidad;
    }

    public void setNombre_localidad(String nombre_localidad) {
        this.nombre_localidad = nombre_localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
