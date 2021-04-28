package com.pk.arrowcenter.Utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class FirebaseUtils {

    // Vars
    private static final String TAG = "FirebaseUtils";

    /**
     * Subscribes to device topic with device codename to receive messages from FCM
     */
    public void subscribeToDevice() {
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.DEVICE_TOPIC)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "subscribeToDevice: failed!");
                        } else
                            Log.d(TAG, "subscribeToDevice: successful: " + Constants.DEVICE_TOPIC);
                    }
                });
    }

    /**
     * Subscribes to Arrow announcement topic to receive messages from FCM
     */
    public void subscribeToArrow() {
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.ARROW_TOPIC)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "subscribeToArrow: failed!");
                        } else
                            Log.d(TAG, "subscribeToArrow: successful");
                    }
                });
    }

}
