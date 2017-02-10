package dev.levkovskiy.batterymanager.utils;

import android.content.Context;
import android.content.Intent;

import dev.levkovskiy.batterymanager.ui.activity.BatteryActivity;
import dev.levkovskiy.batterymanager.ui.activity.MainActivity;
import dev.levkovskiy.batterymanager.ui.activity.RamActivity;
import dev.levkovskiy.batterymanager.ui.activity.StorageActivity;


public class ActivityLauncher {

    public static void startMainActivity(Context ctx) {
        Intent intent = new Intent(ctx, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    public static void startStorageActivity(Context ctx) {
        Intent intent = new Intent(ctx, StorageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    public static void startBatteryActivity(Context ctx) {
        Intent intent = new Intent(ctx, BatteryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    public static void startRamActivity(Context ctx) {
        Intent intent = new Intent(ctx, RamActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }
}
