package vacunasuy.componentemovil.obj;

import java.util.Date;

public class DtAgenda {
    Integer id;
    Date fecha;

    public DtAgenda(Integer id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
