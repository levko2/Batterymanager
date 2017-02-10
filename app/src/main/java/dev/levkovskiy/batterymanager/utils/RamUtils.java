package dev.levkovskiy.batterymanager.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RamUtils {

    private Context context;

    public RamUtils(Context context) {
        this.context = context;
    }

    public long calculateTotalRAM() {
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;

        long initialMemory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();//meminfo
            arrayOfString = str2.split("\\s+");

            //total Memory
            initialMemory = Integer.valueOf(arrayOfString[1]).intValue() << 10;
            localBufferedReader.close();
            return initialMemory;
        } catch (IOException e) {
            return -1;
        }
    }

    public long calculateAvailableRAM() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity
                .ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);

        return mi.availMem;
    }
}

