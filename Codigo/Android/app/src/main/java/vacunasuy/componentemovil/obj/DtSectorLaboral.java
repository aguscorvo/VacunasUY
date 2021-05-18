package vacunasuy.componentemovil.obj;

public class DtSectorLaboral {
    private Integer id;
    private String nombre;

    public DtSectorLaboral(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

}
