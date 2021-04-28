package com.pk.arrowcenter.ui.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pk.arrowcenter.R;
import com.pk.arrowcenter.Utils.FirebaseUtils;
import com.pk.arrowcenter.Utils.NotificationDataUtils;
import com.pk.arrowcenter.models.NotificationModel;
import com.pk.arrowcenter.ui.adapters.NotificationAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Vars
    private RecyclerView recyclerView;
    private NotificationDataUtils dataUtils;

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

        // Initialize notification item swipe listener
        initTouchHelper();
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
        dataUtils = new NotificationDataUtils(this);

        NotificationAdapter notificationAdapter = new NotificationAdapter(dataUtils.getSavedNotifications());
        recyclerView.setAdapter(notificationAdapter);
    }


    /**
     * Updates notification list on swipe gesture
     *
     * @param position index of swiped item
     */
    private void handleOnSwiped(int position) {
        ArrayList<NotificationModel> notificationsList = dataUtils.getSavedNotifications();
        notificationsList.remove(position);
        dataUtils.updateNotificationList(notificationsList);
    }

    /**
     * Registers item move and swipe listener on {@link NotificationAdapter}
     */
    private void initTouchHelper() {
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                handleOnSwiped(viewHolder.getAdapterPosition());
            }
        });
        touchHelper.attachToRecyclerView(recyclerView);
    }
}
