package materialtest.jufequinta.navigationdrawer.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by acer on 25/02/2016.
 */
public class HandlerDataBase extends SQLiteOpenHelper{
    public HandlerDataBase(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table ItunesApp(nameApp text, imageLink text, labelCategory text, description text, released text, artist text, releaseDate text, link text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists ItunesApp");
        db.execSQL("create table ItunesApp(dni nameApp text, imageLink text, labelCategory text, description text, released text, artist text, releaseDate text, link text)");
    }
}
