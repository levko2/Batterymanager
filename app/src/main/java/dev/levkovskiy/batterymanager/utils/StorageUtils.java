package dev.levkovskiy.batterymanager.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;


public class StorageUtils {
    private Context context;

    public StorageUtils(Context context) {
        this.context = context;
    }

    public static final long weight = 1048576;

    public String getExternalBasePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public long getExternalTotalStorage() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());

        long blockCount = statFs.getBlockCount();
        long blockSize = statFs.getBlockSize();

        return blockCount * blockSize;
    }

    public long getExternalFreeStorage() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());

        long availableBlocks = statFs.getAvailableBlocks();
        long blockSize = statFs.getBlockSize();

        return availableBlocks * blockSize;
    }

    public long getExternalUsedStorage() {
        return getExternalTotalStorage() - getExternalFreeStorage();
    }

    public long getMegabyteFromByte(long bytes) {
        return bytes / (1024 * 1024);
    }

    public void deleteCache() {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}