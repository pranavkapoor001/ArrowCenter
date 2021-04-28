package com.pk.arrowcenter.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pk.arrowcenter.Utils.NotificationDataUtils;
import com.pk.arrowcenter.Utils.NotificationUtils;

public class FirebaseService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseService";

    /*
     * App state	Notification	    Data	            Both
     * Foreground	onMessageReceived	onMessageReceived	onMessageReceived
     * Background	System tray	        onMessageReceived	Notification: system tray
     *                                                      (Data: in extras of the intent)
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "Message received");

        // Save new notification
        String topic = remoteMessage.getFrom();
        NotificationDataUtils dataUtils = new NotificationDataUtils(this);

        // Get title and message and store notification
        final String title = remoteMessage.getData().get("title");
        final String message = remoteMessage.getData().get("body");
        dataUtils.addNotification(topic, title, message);

        // Deliver notification to system tray
        new NotificationUtils(this).postNotification(title, message);
    }
}
