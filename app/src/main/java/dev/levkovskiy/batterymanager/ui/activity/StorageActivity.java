package dev.levkovskiy.batterymanager.ui.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.UiThread;

import butterknife.BindView;
import butterknife.OnClick;
import dev.levkovskiy.batterymanager.R;
import dev.levkovskiy.batterymanager.animation.PieAngleAnimation;
import dev.levkovskiy.batterymanager.ui.view.LoadingView;
import dev.levkovskiy.batterymanager.ui.view.PieView;

public class StorageActivity extends BaseActivity {

    @BindView(R.id.loading_view)
    LoadingView loadingView;
    @BindView(R.id.chStorage)
    PieView chStorage;
    @BindView(R.id.tvStorage)
    TextView tvStorage;
    @BindView(R.id.llStat)
    LinearLayout llStat;
    private PieAngleAnimation storageAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupStorageUi();
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
    private void addStorageAnimations() {
        storageAnim = new PieAngleAnimation(chStorage);
        storageAnim.setDuration(3000);
        chStorage.startAnimation(storageAnim);

    }

    @Override
    protected int obtainContentViewLayoutId() {
        return R.layout.activity_storage;
    }

    @OnClick(R.id.btnClean)
    public void cleanCache() {
        app.getStorageUtils().deleteCache();
    }
}
