package vacunasuy.componentemovil.bd;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Esta clase devuelve una instancia de la interfaz controlerDAO.
 */

@Database(entities = {Usuario.class, Mensaje.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {
    public abstract controlerDAO controlerDAO();
}


