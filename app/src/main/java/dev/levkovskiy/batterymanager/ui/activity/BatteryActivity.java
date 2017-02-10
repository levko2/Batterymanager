package dev.levkovskiy.batterymanager.ui.activity;

import android.os.Bundle;

import dev.levkovskiy.batterymanager.R;

public class BatteryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int obtainContentViewLayoutId() {
        return R.layout.activity_battery;
    }
}
