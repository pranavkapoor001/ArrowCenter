package com.pk.arrowcenter.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    // Vars
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private NotificationDataUtils dataUtils;

    // UI Components
    private TextView tvNoNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get views
        recyclerView = findViewById(R.id.notification_recycler_view);
        tvNoNotifications = findViewById(R.id.empty_view);

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

        // Show No Notifications text if empty
        showEmptyText();
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
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);

        // Get data
        dataUtils = new NotificationDataUtils(this);

        notificationAdapter = new NotificationAdapter(dataUtils.getSavedNotifications());
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

        // Show No Notifications text if empty
        showEmptyText();
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

    /**
     * Displays No Notifications text if there are no items in recycler view
     */
    private void showEmptyText() {
        // Show no notifications text conditionally
        if (notificationAdapter.getItemCount() == 0)
            tvNoNotifications.setVisibility(View.VISIBLE);
        else
            tvNoNotifications.setVisibility(View.GONE);
    }


    /*------------------------------- Lifecycle methods ------------------------------------------*/
    // Register OnSharedPreferenceChangeListener in onResume() and unregister in onPause()
    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Update data in recycler view then list
        NotificationAdapter adapter = (NotificationAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.updateNotifications(dataUtils.getSavedNotifications());
            adapter.notifyDataSetChanged();
        }
    }
}
