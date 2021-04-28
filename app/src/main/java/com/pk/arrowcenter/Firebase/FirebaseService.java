package com.pk.arrowcenter.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pk.arrowcenter.Utils.NotificationDataUtils;

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

        if (remoteMessage.getNotification() == null)
            return;

        // Save new notification
        String topic = remoteMessage.getFrom();
        NotificationDataUtils dataUtils = new NotificationDataUtils(this);
        dataUtils.addNotification(topic, remoteMessage.getNotification());
    }
}
