package vacunasuy.componentemovil.bd;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

/**
 * Clase que hace uso de la interfaz controlerDAO
 */
public class AccesoBD {
    private static AccesoBD instancia;

    private controlerDAO mcontrolerDAO;

    private AccesoBD(Context context){
        Context appContext = context.getApplicationContext();
        AppDataBase db = Room.databaseBuilder(appContext, AppDataBase.class, "VacunasUY")
                .allowMainThreadQueries()
                .build();
        mcontrolerDAO = db.controlerDAO();
    }

    public static AccesoBD getInstance(Context context){
        if(instancia == null){
            instancia = new AccesoBD(context);
        }
        return instancia;
    }

    public List<Usuario> getUsuarios(){
        return mcontrolerDAO.getAllUsers();
    }

    public List<Usuario> findUserByName(String nombre){
        return mcontrolerDAO.findUserByName(nombre);
    }

    public void addUsuario(Usuario usuario){
        mcontrolerDAO.insertUsuario(usuario);
    }

    public Usuario getUsuarioById(Integer id){
        return mcontrolerDAO.findUserById(id);
    }

    public void deleteUsuario(Usuario usuario){
        mcontrolerDAO.deleteUsuario(usuario);
    }

    public void deleteMensaje(Mensaje mensaje){
        mcontrolerDAO.deleteMensaje(mensaje);
    }

    public List<Mensaje> getMensajes(){
        return mcontrolerDAO.getAllMensajes();
    }

    public List<Mensaje> getMensajesByUsuarioID(Integer id){
        return mcontrolerDAO.getAllMensajesByUsuario(id);
    }

    public Mensaje getMensajeById(Integer id){
        return mcontrolerDAO.findMensajeById(id);
    }

    public void addMensaje(Mensaje mensaje){
        mcontrolerDAO.insertMensaje(mensaje);
    }
}
