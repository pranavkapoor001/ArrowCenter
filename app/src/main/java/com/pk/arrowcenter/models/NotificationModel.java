package com.pk.arrowcenter.models;

/**
 * Serves as data class for storing FCM notification
 */
public class NotificationModel {

    private final String topic;
    private final String title;
    private final String message;

    public NotificationModel(String topic, String title, String message) {
        this.topic = topic;
        this.title = title;
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
