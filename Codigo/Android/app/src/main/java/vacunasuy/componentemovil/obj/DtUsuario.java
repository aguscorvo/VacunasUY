package vacunasuy.componentemovil.obj;

import java.util.Date;
import java.util.List;

public class DtUsuario {
    private static DtUsuario uInstance= null;
    private String nombre;
    private String apellido;
    private String token;
    private Date fechanacimiento;
    private Integer id;
    private String documento;
    private String correo;
    private Boolean registrado = false;
    private List<DtRol> roles;

    protected DtUsuario(){}

    public static synchronized DtUsuario getInstance() {
        if(uInstance == null){
            uInstance = new DtUsuario();
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<DtRol> getRoles() {
        return roles;
    }

    public void setRoles(List<DtRol> roles) {
        this.roles = roles;
    }
}
