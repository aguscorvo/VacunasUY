package vacunasuy.componentemovil.notification;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificacionFirebase extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("Token", s);
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", s).apply();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("Notificacion Firebase", remoteMessage.getNotification().getTitle());
        Log.d("Notificacion Firebase", remoteMessage.getNotification().getBody());
        /*
            Acá la idea es armar un TOAST que muestre la notificación.
            Es muy a pulmón. Hay que mostrar getTitle() y getBody()
        */
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
    }
}
