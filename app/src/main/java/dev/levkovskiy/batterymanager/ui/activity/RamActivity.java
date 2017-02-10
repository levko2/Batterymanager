package dev.levkovskiy.batterymanager.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ram.speed.booster.RAMBooster;
import com.ram.speed.booster.interfaces.CleanListener;
import com.ram.speed.booster.interfaces.ScanListener;
import com.ram.speed.booster.utils.ProcessInfo;

import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import dev.levkovskiy.batterymanager.R;
import dev.levkovskiy.batterymanager.animation.PieAngleAnimation;
import dev.levkovskiy.batterymanager.ui.view.LoadingView;
import dev.levkovskiy.batterymanager.ui.view.PieView;
import dev.levkovskiy.batterymanager.utils.StorageUtils;

public class RamActivity extends BaseActivity {

    @BindView(R.id.chRam)
    PieView chRam;
    @BindView(R.id.tvRam)
    TextView tvRam;
    @BindView(R.id.btnClean)
    Button btnClean;
    @BindView(R.id.loading_view)
    LoadingView loadingView;
    private PieAngleAnimation ramAnim;
    private RAMBooster booster;
    public static final String TAG = RamActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int obtainContentViewLayoutId() {
        return R.layout.activity_ram;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRamUi();
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
    private void addRamAnimations() {
        ramAnim = new PieAngleAnimation(chRam);
        ramAnim.setDuration(3000);
        chRam.startAnimation(ramAnim);
    }

    @OnClick(R.id.btnClean)
    public void cleanClick() {
        if (booster == null)
            booster = null;
        booster = new RAMBooster(RamActivity.this);
        booster.setDebug(true);
        booster.setScanListener(new ScanListener() {
            @Override
            public void onStarted() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingView.setVisibility(View.VISIBLE);
                        loadingView.start();
                    }
                });

                Log.d(TAG, "Scan started");
            }

            @Override
            public void onFinished(long availableRam, long totalRam, List<ProcessInfo> appsToClean) {

                Log.d(TAG, String.format(Locale.US,
                        "Scan finished, available RAM: %dMB, total RAM: %dMB",
                        availableRam, totalRam));
                List<String> apps = new ArrayList<String>();
                for (ProcessInfo info : appsToClean) {
                    apps.add(info.getProcessName());
                }
                Log.d(TAG, String.format(Locale.US,
                        "Going to clean founded processes: %s", Arrays.toString(apps.toArray())));
                booster.startClean();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingView.setVisibility(View.GONE);
                    }
                });
            }
        });
        booster.setCleanListener(new CleanListener() {
            @Override
            public void onStarted() {
                Log.d(TAG, "Clean started");
            }


            @Override
            public void onFinished(long availableRam, long totalRam) {

                Log.d(TAG, String.format(Locale.US,
                        "Clean finished, available RAM: %dMB, total RAM: %dMB",
                        availableRam, totalRam));
                booster = null;


            }
        });
        booster.startScan(true);
    }


}
