package vacunasuy.componentemovil.obj;

public class DtEnfermedad {
    private Integer id;
    private String nombre;

    public DtEnfermedad(Integer id, String nombre) {
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
