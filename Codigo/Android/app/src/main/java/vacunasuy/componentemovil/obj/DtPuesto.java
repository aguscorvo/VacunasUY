package vacunasuy.componentemovil.obj;

import java.util.List;

public class DtPuesto {
    private Integer id;
    private Integer numero;
    List<DtAgenda> agenda;

    public DtPuesto(Integer id, Integer numero, List<DtAgenda> agenda) {
        this.id = id;
        this.numero = numero;
        this.agenda = agenda;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumero() {
        return numero;
    }

    public List<DtAgenda> getAgenda() {
        return agenda;
    }
}
