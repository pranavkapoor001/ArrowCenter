package com.pk.arrowcenter.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

import com.pk.arrowcenter.R;
import com.pk.arrowcenter.ui.activities.MainActivity;

public class NotificationUtils {

    private static final String PRIMARY_CHANNEL_ID = "primary_channel_id";
    private final Context mContext;
    private final NotificationManager mNotifyManager;

    public NotificationUtils(Context context) {
        mContext = context;
        mNotifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        /* Create notification channel
         * Note: It is safe here since creating channel with same id has no effect
         * if it already exists
         */
        initNotificationChannel();
    }

    /**
     * Delivers notification to system tray
     *
     * @param title   Notification title
     * @param message Notification text
     */
    public void postNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                mContext, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_arrow)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(getIntent())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_SYSTEM);

        // Notify
        mNotifyManager.notify(101, builder.build());
    }

    /**
     * Creates notification channel
     */
    private void initNotificationChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(
                PRIMARY_CHANNEL_ID, "ArrowCenter", NotificationManager.IMPORTANCE_HIGH);

        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.GREEN);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription(mContext.getString(R.string.notification_channel_description));
        mNotifyManager.createNotificationChannel(notificationChannel);
    }

    /**
     * Builds pending intent leading to {@link MainActivity} on notification click
     *
     * @return PendingIntent
     */
    private PendingIntent getIntent() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(
                mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
