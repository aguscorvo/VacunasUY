package vacunasuy.componentemovil.bd;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 *  Define 2 funciones, una que convierte un objeto Date en un objeto Long y otro que realiza la conversión inversa, de Long a Date.
 *  Dado que Room ya sabe cómo persistir los objetos Long, puede usar este convertidor para conservar los valores del tipo Date.
 */

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date){
        return date == null ? null : date.getTime();
    }
}
