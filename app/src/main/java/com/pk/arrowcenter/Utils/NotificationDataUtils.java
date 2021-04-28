package com.pk.arrowcenter.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pk.arrowcenter.models.NotificationModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NotificationDataUtils {

    private static final String TAG = "NotificationDataUtils";
    private final Context mContext;

    public NotificationDataUtils(Context context) {
        mContext = context;
    }

    /**
     * Adds new notification to notification list
     *
     * @param topic        FCM topic for notification
     * @param notification object containing FCM notification data
     */
    public void addNotification(String topic, RemoteMessage.Notification notification) {
        // Get saved notifications and append new one
        ArrayList<NotificationModel> notificationsList = getSavedNotifications();
        notificationsList.add(new NotificationModel(topic, notification.getTitle(), notification.getBody()));

        // Save
        updateNotificationList(notificationsList);
    }

    /**
     * Saves updated notification list  of type {@link NotificationModel} to shared preferences
     *
     * @param notificationsList Updated Notification list
     */
    public void updateNotificationList(ArrayList<NotificationModel> notificationsList) {
        // Save updated notifications list as gson object
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notificationsList);
        editor.putString(Constants.PREFS_KEY, json).apply();
    }

    /**
     * Gets gson of saved notifications list from shared preferences
     * and converts it back to list of type {@link NotificationModel}
     *
     * @return List containing saved notifications
     */
    private ArrayList<NotificationModel> getSavedNotifications() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();
        String json = prefs.getString(Constants.PREFS_KEY, null);
        Type type = new TypeToken<ArrayList<NotificationModel>>() {
        }.getType();

        // List can be null when fetching for first time, return new list
        if (gson.fromJson(json, type) == null)
            return new ArrayList<>();
        else
            return gson.fromJson(json, type);
    }
}
