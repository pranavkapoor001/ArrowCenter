package com.pk.arrowcenter.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pk.arrowcenter.R;
import com.pk.arrowcenter.Utils.FirebaseUtils;
import com.pk.arrowcenter.Utils.NotificationDataUtils;
import com.pk.arrowcenter.ui.adapters.NotificationAdapter;

public class MainActivity extends AppCompatActivity {

    // Vars
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get views
        recyclerView = findViewById(R.id.notification_recycler_view);

        // Initialize everything
        init();
    }

    /**
     * Initializes everything required
     */
    private void init() {
        // Firebase subscribe
        subscribe();

        // Initialize recycler view
        initNotificationList();
    }

    /**
     * Subscribes to FCM topic via {@link FirebaseUtils}
     */
    private void subscribe() {
        FirebaseUtils firebaseUtils = new FirebaseUtils();
        firebaseUtils.subscribeToDevice();
        firebaseUtils.subscribeToArrow();
    }

    /**
     * Initializes recycler view with all notifications
     */
    private void initNotificationList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        // Get data
        NotificationDataUtils dataUtils = new NotificationDataUtils(this);

        NotificationAdapter notificationAdapter = new NotificationAdapter(dataUtils.getSavedNotifications());
        recyclerView.setAdapter(notificationAdapter);
    }
}
