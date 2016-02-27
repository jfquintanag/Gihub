package materialtest.jufequinta.navigationdrawer.Controller.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import materialtest.jufequinta.navigationdrawer.Controller.Adapters.DetailAdapter;
import materialtest.jufequinta.navigationdrawer.Message.Entities.ItunesApp;
import materialtest.jufequinta.navigationdrawer.R;
/**
 * Created by acer on 25/02/2016.
 */
public class ItemDetailActivity  extends AppCompatActivity{
    Toolbar toolbar;
    TextView txtSettings;
    ArrayList<ItunesApp> lstListApp;
    ListView lstViewApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        identifiedTabletSmartphone();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lstViewApp = (ListView) findViewById(R.id.lstItemDetail);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        ItemDetailActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }
        try{
            Bundle extra = getIntent().getBundleExtra("extra");
            lstListApp = (ArrayList<ItunesApp>) extra.getSerializable("objects");

            DetailAdapter adapter = new DetailAdapter(this, lstListApp);
            lstViewApp.setAdapter(adapter);
            lstViewApp.setDivider(null);
            lstViewApp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ItunesApp itunesApp = lstListApp.get(position);

                    Bundle extra = new Bundle();
                    extra.putSerializable("appDetail", itunesApp);

                    Intent intent = new Intent(view.getContext(), AppDetailActivity.class);
                    intent.putExtra("extra",extra);
                    startActivity(intent);
                }
            });
            if(lstListApp.size()>0)
                setTitle(lstListApp.get(0).getLabelCategory());

        }catch (Exception exe){

        }
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
