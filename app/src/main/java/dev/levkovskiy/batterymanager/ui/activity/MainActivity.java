package dev.levkovskiy.batterymanager.ui.activity;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.UiThread;

import butterknife.BindView;
import butterknife.OnClick;
import dev.levkovskiy.batterymanager.R;
import dev.levkovskiy.batterymanager.animation.PieAngleAnimation;
import dev.levkovskiy.batterymanager.listeners.BatteryListener;
import dev.levkovskiy.batterymanager.receivers.BatteryLevelReceiver;
import dev.levkovskiy.batterymanager.ui.view.PieView;
import dev.levkovskiy.batterymanager.utils.ActivityLauncher;
import dev.levkovskiy.batterymanager.utils.StorageUtils;


public class MainActivity extends BaseActivity implements BatteryListener {
    @BindView(R.id.tvRam)
    TextView tvRam;
    @BindView(R.id.tvBattery)
    TextView tvBattery;
    @BindView(R.id.tvStorage)
    TextView tvStorage;
    BatteryLevelReceiver receiver;
    @BindView(R.id.chBattery)
    PieView chBattery;
    @BindView(R.id.chStorage)
    PieView chStorage;
    @BindView(R.id.chRam)
    PieView chRam;
    PieAngleAnimation batteryAnim, ramAnim, storageAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver = new BatteryLevelReceiver(this);
        this.registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected int obtainContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRamUi();
        setupStorageUi();
        addBatteryAnimations();
    }

    @OnClick({R.id.chBattery, R.id.chRam, R.id.chStorage})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.chBattery:
                ActivityLauncher.startBatteryActivity(getApplicationContext());
                break;
            case R.id.chRam:
                ActivityLauncher.startRamActivity(getApplicationContext());
                break;
            case R.id.chStorage:
                ActivityLauncher.startStorageActivity(getApplicationContext());
                break;
        }
    }

    @UiThread
    private void setupRamUi() {
        long total = app.getRamUtils().calculateTotalRAM();
        long current = total - app.getRamUtils().calculateAvailableRAM();
        tvRam.setText(total / StorageUtils.weight + " - total\n" + app.getRamUtils().calculateAvailableRAM() / StorageUtils.weight + " - available");
        float percentage = (current / (float) total) * 100;
        chRam.setPercentage((long) percentage);
        addRamAnimations();
    }

    @UiThread
    private void setupStorageUi() {
        long total = app.getStorageUtils().getExternalTotalStorage();
        long current = app.getStorageUtils().getExternalUsedStorage();
        float percentage = (current / (float) total) * 100;
        chStorage.setPercentage((long) percentage);
        addStorageAnimations();
        tvStorage.setText(app.getStorageUtils().getMegabyteFromByte(total) + " total\n"
                + app.getStorageUtils().getMegabyteFromByte(current) + " used\n"
                + app.getStorageUtils().getMegabyteFromByte(app.getStorageUtils().getExternalFreeStorage()) + " free");

    }

    @UiThread
    private void addBatteryAnimations() {
        batteryAnim = new PieAngleAnimation(chBattery);
        batteryAnim.setDuration(3000);
        chBattery.startAnimation(batteryAnim);
    }

    @UiThread
    private void addRamAnimations() {
        ramAnim = new PieAngleAnimation(chRam);
        ramAnim.setDuration(3000);
        chRam.startAnimation(ramAnim);

    }

    @UiThread
    private void addStorageAnimations() {
        storageAnim = new PieAngleAnimation(chStorage);
        storageAnim.setDuration(3000);
        chStorage.startAnimation(storageAnim);

    }


    @Override
    public void setLevelReceived(float level) {
        level *= 100;
        chBattery.setPercentage(level);
        addBatteryAnimations();
        tvBattery.setText((long) level + " % charged");
    }
}
