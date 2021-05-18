package vacunasuy.componentemovil.obj;

import java.util.List;

public class DtDepartamento {
    private Integer id;
    private String nombre;
    List<DtLocalidad> localidades;

    public DtDepartamento() {
    }

    public DtDepartamento(Integer id, String nombre, List<DtLocalidad> localidades) {
        this.id = id;
        this.nombre = nombre;
        this.localidades = localidades;
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

    public List<DtLocalidad> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<DtLocalidad> localidades) {
        this.localidades = localidades;
    }
}
