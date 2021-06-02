package vacunasuy.componentemovil.notification;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificacionFirebase extends FirebaseMessagingService {

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

}
