package com.example.loransmubarikyproj.Class;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.loransmubarikyproj.R;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmNotification extends BroadcastReceiver {
    private static int count = 0;
    @RequiresApi(api= Build.VERSION_CODES.Q)
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_01")
                .setSmallIcon(R.drawable.baseline_notification_important_24)
                .setContentTitle("Reminder")
                .setContentText("It's time to wake up")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        count++;

    }
}
