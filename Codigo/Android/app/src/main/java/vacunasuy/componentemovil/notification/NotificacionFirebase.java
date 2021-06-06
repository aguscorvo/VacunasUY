package vacunasuy.componentemovil.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import vacunasuy.componentemovil.AccessActivity;
import vacunasuy.componentemovil.R;
import vacunasuy.componentemovil.second.MiAgenda;

public class NotificacionFirebase extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private  static String CHANNEL_ID = "NF_VacunasUY";
    private  static Integer NOTIFICATION_ID = 0;

    // [START on_new_token]
    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        //sendRegistrationToServer(token);
    }



    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, remoteMessage.getNotification().getTitle());
        Log.d(TAG, remoteMessage.getNotification().getBody());
        /*
            Se muesta l notificacion
        */
        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     * @param messageTitle FCM title received.
     */
    private void sendNotification(String messageBody, String messageTitle) {

        //Creación de un explicit intent para iniciar la actividad en nuestra app
        Intent intent = new Intent(NotificacionFirebase.this, MiAgenda.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //Definicion de tono de notificacion
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Armado del a notificacion
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(NotificacionFirebase.this, CHANNEL_ID)
                        //Notification.Builder mBuilder =
                        //        new Notification.Builder(MainActivity.this)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
                        .setSound(defaultSoundUri)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }

}
