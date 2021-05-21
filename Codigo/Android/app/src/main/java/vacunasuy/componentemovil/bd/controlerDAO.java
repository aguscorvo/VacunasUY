package vacunasuy.componentemovil.bd;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
/**
 * Esta interfaz define los métodos para interactuar con la BD
 */
@Dao
public interface controlerDAO {
    @Query("SELECT * FROM usuarios")
    List<Usuario> getAllUsers();

    @Query("SELECT * FROM usuarios WHERE id = :id")
    Usuario findUserById(Integer id);

    @Query("SELECT * FROM usuarios WHERE name LIKE :name")
    List<Usuario> findUserByName(String name);

    @Insert
    void insertUsuario(Usuario usuario);

    @Delete
    void deleteUsuario(Usuario usuario);

    @Insert
    void insertMensaje(Mensaje mensaje);

    @Delete
    void deleteMensaje(Mensaje mensaje);

    @Query("SELECT * FROM mensajes")
    List<Mensaje> getAllMensajes();


    @Query("SELECT * FROM mensajes WHERE usuario_id = :usuario_id")
    List<Mensaje> getAllMensajesByUsuario(Integer usuario_id);

    @Query("SELECT * FROM mensajes WHERE id = :id")
    Mensaje findMensajeById(Integer id);

}
