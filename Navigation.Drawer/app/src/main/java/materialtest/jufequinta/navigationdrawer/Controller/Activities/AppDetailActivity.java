package materialtest.jufequinta.navigationdrawer.Controller.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import materialtest.jufequinta.navigationdrawer.Message.Entities.ItunesApp;
import materialtest.jufequinta.navigationdrawer.R;

/**
 * Created by acer on 27/02/2016.
 */
public class AppDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    ItunesApp itunesApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        identifiedTabletSmartphone();
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        AppDetailActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }
        try{
            Bundle extra = getIntent().getBundleExtra("extra");
            itunesApp = (ItunesApp) extra.getSerializable("appDetail");

            TextView lblNameAppDetail=(TextView)findViewById(R.id.lblNameAppDetail);
            TextView lblNameArtistDetail=(TextView)findViewById(R.id.lblNameArtistDetail);
            TextView lblDescriptionAppDetail=(TextView)findViewById(R.id.lblDescriptionAppDetail);
            ImageView imgApplicationDetail = (ImageView)findViewById(R.id.imgApplicationDetail);

            lblNameAppDetail.setText(itunesApp.getNameApp());
            lblNameArtistDetail.setText(itunesApp.getArtist());
            lblDescriptionAppDetail.setText(itunesApp.getDescription());

            byte[] decodedString = Base64.decode(itunesApp.getImageLink(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            Resources res = getResources();

            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, decodedByte);
            dr.setCornerRadius(Math.max(decodedByte.getWidth(), decodedByte.getHeight()) / 8.0f);
            imgApplicationDetail.setImageDrawable(dr);

            imgApplicationDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(itunesApp.getLink()));
                    startActivity(i);
                }
            });

            setTitle("Descripción del app");

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
