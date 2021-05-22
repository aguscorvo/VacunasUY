package vacunasuy.componentemovil.bd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 *  Objeto Usuario,
 */
@Entity(tableName = "usuarios")
public class Usuario {

    @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "documento")
    private String documento;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "apellido")
    private String apellido;

    public Usuario(Integer id, String documento, String name, String apellido) {
        this.documento = documento;
        this.name = name;
        this.apellido = apellido;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
