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
 * Created by acer on 25/02/2016.
 */
public class DetailAdapter extends ArrayAdapter<ItunesApp> {
    public DetailAdapter(Context context, ArrayList<ItunesApp> appArrayList) {
        super(context, 0, appArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItunesApp itunesApp = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.detail_item, parent, false);
        }
        TextView lblNameApp = (TextView) convertView.findViewById(R.id.lblNameApp);
        TextView lblNameArtist = (TextView) convertView.findViewById(R.id.lblNameArtist);
        TextView lblDescriptionApp = (TextView) convertView.findViewById(R.id.lblDescriptionApp);
        ImageView imgFotoApp = (ImageView) convertView.findViewById(R.id.imgApplication);

        lblNameApp.setText(itunesApp.getNameApp());
        lblNameArtist.setText("by "+itunesApp.getArtist()+"\n");
        if(itunesApp.getDescription().length()>180)
            lblDescriptionApp.setText(itunesApp.getDescription().substring(0,180)+"...");
        else
            lblDescriptionApp.setText(itunesApp.getDescription());

        byte[] decodedString = Base64.decode(itunesApp.getImageLink(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        Resources res = getContext().getResources();

        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, decodedByte);
        dr.setCornerRadius(Math.max(decodedByte.getWidth(), decodedByte.getHeight()) / 8.0f);

        imgFotoApp.setImageDrawable(dr);
        return convertView;
    }
}
