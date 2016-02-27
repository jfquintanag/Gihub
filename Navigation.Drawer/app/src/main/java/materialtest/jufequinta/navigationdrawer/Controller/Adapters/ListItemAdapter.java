package materialtest.jufequinta.navigationdrawer.Controller.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import materialtest.jufequinta.navigationdrawer.Message.Entities.ItunesApp;
import materialtest.jufequinta.navigationdrawer.R;

/**
 * Created by acer on 27/02/2016.
 */
public class ListItemAdapter extends ArrayAdapter<ItunesApp> {
    ArrayList<ItunesApp> lstItemsApp;
    public ListItemAdapter(Context context, ArrayList<ItunesApp> lstItemsApp) {
        super(context, 0, lstItemsApp);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItunesApp itunesApp = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_menu_item, parent, false);
        }
        TextView lblNameAppList  = (TextView) convertView.findViewById(R.id.lblNameArtistList);
        ImageView imgAplicacionList =(ImageView)convertView.findViewById(R.id.imgAplicacionList);
        TextView lblNameArtistList=(TextView) convertView.findViewById(R.id.lblNameArtistList);
        TextView lblReleaseDateList=(TextView) convertView.findViewById(R.id.lblReleaseDateList);

        byte[] decodedString = Base64.decode(itunesApp.getImageLink(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        Resources res = getContext().getResources();

        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, decodedByte);
        dr.setCornerRadius(Math.max(decodedByte.getWidth(), decodedByte.getHeight()) / 8.0f);

        lblNameAppList.setText(itunesApp.getNameApp());
        imgAplicacionList.setImageDrawable(dr);
        lblNameArtistList.setText(itunesApp.getArtist());
        lblReleaseDateList.setText(itunesApp.getReleaseDate());

        return convertView;
    }
}
