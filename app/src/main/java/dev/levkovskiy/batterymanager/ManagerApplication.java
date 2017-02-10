package dev.levkovskiy.batterymanager;

import android.app.Application;

import dev.levkovskiy.batterymanager.utils.BatteryUtils;
import dev.levkovskiy.batterymanager.utils.RamUtils;
import dev.levkovskiy.batterymanager.utils.StorageUtils;


public class ManagerApplication extends Application {
    BatteryUtils batteryUtils;
    StorageUtils storageUtils;
    RamUtils ramUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        batteryUtils = new BatteryUtils(getBaseContext());
        storageUtils = new StorageUtils(getBaseContext());
        ramUtils = new RamUtils(getBaseContext());

    }

    public RamUtils getRamUtils() {
        return ramUtils;
    }

    public BatteryUtils getBatteryUtils() {
        return batteryUtils;
    }

    public StorageUtils getStorageUtils() {
        return storageUtils;
    }
}
