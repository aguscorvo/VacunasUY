package vacunasuy.componentemovil.bd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 *  Objeto Mensaje,
 */
@Entity(tableName = "mensajes")
public class Mensaje {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "texto")
    private String texto;

    @ColumnInfo(name = "fecha")
    private Date fecha;

    @ColumnInfo(name = "usuario_id")
    private Integer usuario_id;

    public Mensaje(String texto, Date fecha, Integer usuario_id) {
        this.texto = texto;
        this.fecha = fecha;
        this.usuario_id = usuario_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_documento(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }
}
