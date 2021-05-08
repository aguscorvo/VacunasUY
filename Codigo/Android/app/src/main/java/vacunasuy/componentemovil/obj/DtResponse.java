package vacunasuy.componentemovil.obj;

public class DtResponse {
    Boolean ok;
    String mensaje;

    public DtResponse(Boolean ok, String mensaje) {
        this.ok = ok;
        this.mensaje = mensaje;
    }

    public Boolean getOk() {
        return ok;
    }

    public String getMensaje() {
        return mensaje;
    }
}
