package com.pk.arrowcenter.ui.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pk.arrowcenter.R;
import com.pk.arrowcenter.Utils.Constants;
import com.pk.arrowcenter.models.NotificationModel;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    // Vars
    private ArrayList<NotificationModel> mNotificationsList;

    // UI Components
    private TextView tvNotificationTopic, tvNotificationTitle, tvNotificationMessage;

    public NotificationAdapter(ArrayList<NotificationModel> notificationsList) {
        mNotificationsList = notificationsList;
    }

    /**
     * Updates notifications list
     *
     * @param notificationsList updated list
     */
    public void updateNotifications(ArrayList<NotificationModel> notificationsList) {
        mNotificationsList = notificationsList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,
                parent, false);

        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        bindTo(position);
    }

    @Override
    public int getItemCount() {
        return mNotificationsList.size();
    }


    /*---------------------------------- View Holder ---------------------------------------------*/

    private void bindTo(int pos) {
        // Set topic
        if (mNotificationsList.get(pos).getTopic().equals(Constants.DEVICE_TOPIC))
            tvNotificationTopic.setText(Build.DEVICE);
        else
            tvNotificationTopic.setText(R.string.arrow);

        tvNotificationTitle.setText(mNotificationsList.get(pos).getTitle());
        tvNotificationMessage.setText(mNotificationsList.get(pos).getMessage());
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNotificationTopic = itemView.findViewById(R.id.notification_topic);
            tvNotificationTitle = itemView.findViewById(R.id.notification_title);
            tvNotificationMessage = itemView.findViewById(R.id.notification_message);
        }
    }
}
