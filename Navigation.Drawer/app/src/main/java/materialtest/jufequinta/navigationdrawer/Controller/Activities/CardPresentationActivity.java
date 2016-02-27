package materialtest.jufequinta.navigationdrawer.Controller.Activities;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import materialtest.jufequinta.navigationdrawer.R;
import materialtest.jufequinta.navigationdrawer.ServicesJSON.AppleServicesJSON_Online;


/**
 * Created by Felipe_Quintana on 24/02/2016.
 */
public class CardPresentationActivity extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_presentation);
        identifiedTabletSmartphone();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setTitle("Información personal");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        CardPresentationActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        AppleServicesJSON_Online appServices = new AppleServicesJSON_Online();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inbox, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        this.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
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