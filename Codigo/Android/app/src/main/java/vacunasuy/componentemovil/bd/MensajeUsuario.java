package vacunasuy.componentemovil.bd;

import androidx.room.Embedded;
import androidx.room.Relation;

public class MensajeUsuario {
    @Embedded
    public Usuario usuario;
    @Relation(
            parentColumn = "documento",
            entityColumn = "usuario_documento"
    )
    public Mensaje mensaje;

}
