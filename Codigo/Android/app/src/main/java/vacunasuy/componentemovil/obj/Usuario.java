package vacunasuy.componentemovil.obj;

import java.util.Date;

public class Usuario {
    private static Usuario uInstance= null;
    private String nombre;
    private String apellido;
    private String token;
    private Date fechanacimiento;
    private Integer id;
    private Boolean registrado = false;

    protected Usuario(){}

    public static synchronized Usuario getInstance() {
        if(uInstance == null){
            uInstance = new Usuario();
        }
        return uInstance;
    }

    public Boolean getRegistrado() {
        return registrado;
    }

    public void setRegistrado(Boolean registrado) {
        this.registrado = registrado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
