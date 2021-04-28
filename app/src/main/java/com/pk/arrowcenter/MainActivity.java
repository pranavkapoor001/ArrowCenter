package com.pk.arrowcenter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pk.arrowcenter.Utils.FirebaseUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase subscribe
        subscribe();
    }

    /**
     * Subscribes to FCM topic via {@link FirebaseUtils}
     */
    private void subscribe() {
        FirebaseUtils firebaseUtils = new FirebaseUtils();
        firebaseUtils.subscribeToDevice();
        firebaseUtils.subscribeToArrow();
    }
}