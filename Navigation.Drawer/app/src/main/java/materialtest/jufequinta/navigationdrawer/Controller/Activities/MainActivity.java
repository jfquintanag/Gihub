package materialtest.jufequinta.navigationdrawer.Controller.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import materialtest.jufequinta.navigationdrawer.Controller.Fragments.MenuGridFragment;
import materialtest.jufequinta.navigationdrawer.Controller.Fragments.MenuListFragment;
import materialtest.jufequinta.navigationdrawer.Message.Entities.Category;
import materialtest.jufequinta.navigationdrawer.Message.Entities.ItunesApp;
import materialtest.jufequinta.navigationdrawer.Model.HandlerDataBase;
import materialtest.jufequinta.navigationdrawer.R;
import materialtest.jufequinta.navigationdrawer.ServicesJSON.AppleServicesJSON_Online;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    ArrayList<ItunesApp> arrayItunesApp;
    ArrayList<Category> arrayCategory;
    ProgressDialog progressDialog;
    int lenUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        identifiedTabletSmartphone();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }

        setupNavigationDrawerContent(navigationView);
        boolean isOnline=isOnline();

        progressDialog = ProgressDialog.show(this, "", "Espere un momento...", true);

        HandlerDataBase admin = new HandlerDataBase(this, "ItunesAppsAdmin", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor cursor = bd.rawQuery("select * from ItunesApp", null);
        if (cursor.moveToFirst()) {
            cursor.close();
            ReadJSONItunes("");
        }else{
            if(isOnline){
                LoadInfoApps();
            }else{
                ReintentLoadInfo();
            }
        }
    }

    public void LoadInfoApps(){
        progressDialog =ProgressDialog.show(this, "", "Espere un momento...", true);
        new HttpAsyncTask().execute(getResources().getString(R.string.urlJSON));
    }

    public void ReintentLoadInfo(){
        progressDialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se requiere de una primera conexión a internet. Quiere volver a intentarlo?")
                .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean isOnline = isOnline();
                        if (isOnline) {
                             LoadInfoApps();
                        } else {
                            ReintentLoadInfo();
                        }
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e){
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onResume(){
        super.onResume();
        this.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void identifiedTabletSmartphone(){
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
    public boolean identifiedTabletSmartphoneBool(){
        return getResources().getBoolean(R.bool.isTablet);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_inbox:
                                menuItem.setChecked(true);
                                setFragment(0);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_settings:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent = new Intent(MainActivity.this, CardPresentationActivity.class);
                                startActivity(intent);
                                return true;
                        }
                        return true;
                    }
                });
    }

    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                boolean flag = identifiedTabletSmartphoneBool();

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
                if(flag){
                    MenuListFragment menuListFragment = new MenuListFragment(arrayCategory, arrayItunesApp);
                    fragmentTransaction.replace(R.id.fragment, menuListFragment);
                    fragmentTransaction.commit();
                }
                else
                {
                    MenuGridFragment menuGridFragment = new MenuGridFragment(arrayCategory, arrayItunesApp);
                    fragmentTransaction.replace(R.id.fragment, menuGridFragment);
                    fragmentTransaction.commit();
                }

                break;
        }
    }

    // Método que se encarga de realizar la des-serialización del JSON.
    public void ReadJSONItunes(String jsonStr) {
        try {
            this.arrayItunesApp = new ArrayList<ItunesApp>();
            this.arrayCategory = new ArrayList<Category>();

            HandlerDataBase admin = new HandlerDataBase(this, "ItunesAppsAdmin", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();

            Cursor cursor = bd.rawQuery("select * from ItunesApp", null);
            if (cursor.moveToFirst()) {
                do {
                    ItunesApp itunesApp = new ItunesApp();
                    Category itemCategoryTmp = new Category();

                    itunesApp.setNameApp(cursor.getString(0));
                    itunesApp.setImageLink(cursor.getString(1));
                    itunesApp.setLabelCategory(cursor.getString(2));
                    itunesApp.setDescription(cursor.getString(3));
                    itunesApp.setReleased(cursor.getString(4));
                    itunesApp.setArtist(cursor.getString(5));
                    itunesApp.setReleaseDate(cursor.getString(6));
                    itunesApp.setLink(cursor.getString(7));
                    arrayItunesApp.add(itunesApp);


                    itemCategoryTmp.setCategoryName(itunesApp.getLabelCategory());

                    addCategoryItunes(itemCategoryTmp);
                } while (cursor.moveToNext());
                bd.close();
                setFragment(0);
                progressDialog.dismiss();
            }
            if (arrayItunesApp.size() <= 0) {
                this.arrayItunesApp = new ArrayList<ItunesApp>();
                this.arrayCategory = new ArrayList<Category>();
                JSONObject json = new JSONObject(jsonStr);
                JSONObject feedObject = json.getJSONObject("feed");
                JSONArray entryArray = feedObject.getJSONArray("entry");
                lenUrl = entryArray.length();
                for (int i = 0; i < entryArray.length(); i++) {
                    ItunesApp objTmp = new ItunesApp();
                    Category objCategoryTmp = new Category();

                    JSONObject entryObjects = entryArray.getJSONObject(i);
                    JSONObject nameObject = entryObjects.getJSONObject("im:name");
                    objTmp.setNameApp(nameObject.getString("label"));

                    JSONObject categoryObject = entryObjects.getJSONObject("category");
                    objTmp.setLabelCategory(categoryObject.getJSONObject("attributes").getString("term"));
                    objCategoryTmp.setCategoryName(categoryObject.getJSONObject("attributes").getString("term"));

                    JSONObject linkObject = entryObjects.getJSONObject("link");
                    objTmp.setLink(linkObject.getJSONObject("attributes").getString("href"));

                    JSONObject summaryObject = entryObjects.getJSONObject("summary");
                    objTmp.setDescription(summaryObject.getString("label"));

                    JSONObject releaseObject = entryObjects.getJSONObject("im:releaseDate");
                    objTmp.setReleased(releaseObject.getString("label"));

                    JSONObject ArtisObject = entryObjects.getJSONObject("im:artist");
                    objTmp.setArtist(ArtisObject.getString("label"));

                    JSONArray imageArray = entryObjects.getJSONArray("im:image");
                    JSONObject imageObjects = imageArray.getJSONObject(2);

                    new DownloadImageTask(objTmp, i, admin).execute(imageObjects.getString("label"));

                    arrayItunesApp.add(objTmp);
                    addCategoryItunes(objCategoryTmp);
                }
            }
        } catch (Exception exe) {
            String a = "";
        }
    }

    public void addCategoryItunes(Category category) {
        boolean flag = false;
        for (int i = 0; i < this.arrayCategory.size(); i++) {
            if (arrayCategory.get(i).getCategoryName().equals(category.getCategoryName()))
                flag = true;
        }
        if (!flag)
            arrayCategory.add(category);
    }


    // Metodos y clases del MainActivity
    // Class privada que se encarga de traer la información de la URL de forma Asynk
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String[] url) {
            return AppleServicesJSON_Online.GET(url[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            ReadJSONItunes(result);
            //txtSettings.setText(result);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ItunesApp itunesAppAsynk;
        int pos;
        HandlerDataBase admin;

        public DownloadImageTask(ItunesApp itunesAppAsynk, int pos, HandlerDataBase admin) {
            this.itunesAppAsynk = itunesAppAsynk;
            this.pos = pos;
            this.admin = admin;
        }

        protected Bitmap doInBackground(String[] urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            result.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            itunesAppAsynk.setImageLink(encoded);
            SQLiteDatabase bd = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();

            registro.put("nameApp", itunesAppAsynk.getNameApp());
            registro.put("imageLink", itunesAppAsynk.getImageLink());
            registro.put("labelCategory", itunesAppAsynk.getLabelCategory());
            registro.put("description", itunesAppAsynk.getDescription());
            registro.put("released", itunesAppAsynk.getReleased());
            registro.put("artist", itunesAppAsynk.getArtist());
            registro.put("releaseDate", itunesAppAsynk.getReleased());
            registro.put("link", itunesAppAsynk.getLink());

            bd.insert("ItunesApp", null, registro);
            bd.close();

            if (pos == (lenUrl - 1)){
                progressDialog.dismiss();
                setFragment(0);
            }
        }
    }
}