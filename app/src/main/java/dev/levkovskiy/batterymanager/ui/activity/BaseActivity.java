package dev.levkovskiy.batterymanager.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;

import butterknife.ButterKnife;
import dev.levkovskiy.batterymanager.ManagerApplication;


public abstract class BaseActivity extends Activity {
    ManagerApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(obtainContentViewLayoutId());
        ButterKnife.bind(this);
        app = (ManagerApplication) getApplication();
    }

    @LayoutRes
    protected abstract int obtainContentViewLayoutId();
}
