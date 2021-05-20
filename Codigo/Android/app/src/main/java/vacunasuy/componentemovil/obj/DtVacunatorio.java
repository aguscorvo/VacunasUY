package vacunasuy.componentemovil.obj;

import java.util.List;

public class DtVacunatorio {
    Integer id;
    String nombre;
    Double latitud;
    Double longitud;
    DtUbicacion ubicacion;
    List<DtPuesto> puestos;

    public DtVacunatorio() {
    }

    public DtVacunatorio(Integer id, String nombre, Double latitud, Double longitud, DtUbicacion ubicacion, List<DtPuesto> puestos) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ubicacion = ubicacion;
        this.puestos = puestos;
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

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public DtUbicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(DtUbicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<DtPuesto> getPuestos() {
        return puestos;
    }

    public void setPuestos(List<DtPuesto> puestos) {
        this.puestos = puestos;
    }
}
