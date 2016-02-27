package materialtest.jufequinta.navigationdrawer.Controller.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

import materialtest.jufequinta.navigationdrawer.R;

/**
 * Created by Felipe_Quintana on 24/02/2016.
 */
public class SplashActivity extends Activity {

    private static final long SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        identifiedTabletSmartphone();
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SCREEN_DELAY);
    }

    public void identifiedTabletSmartphone(){
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}
