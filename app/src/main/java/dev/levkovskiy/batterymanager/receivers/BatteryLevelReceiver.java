package dev.levkovskiy.batterymanager.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import dev.levkovskiy.batterymanager.listeners.BatteryListener;


public class BatteryLevelReceiver extends BroadcastReceiver {
    public static final String TAG = BatteryLevelReceiver.class.getName();
    BatteryListener listener;

    public BatteryLevelReceiver(BatteryListener listener) {
        this.listener = listener;
    }

    public BatteryLevelReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float) scale;
        if (listener != null)
            listener.setLevelReceived(batteryPct);
        Log.i(TAG, batteryPct + " %");
    }
}
