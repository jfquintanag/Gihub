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

import materialtest.jufequinta.navigationdrawer.Message.Entities.Category;
import materialtest.jufequinta.navigationdrawer.Message.Entities.ItunesApp;
import materialtest.jufequinta.navigationdrawer.R;

/**
 * Created by acer on 24/02/2016.
 */

public class CategoryAdapter extends ArrayAdapter<Category> {
    ArrayList<ItunesApp> lstItemsApp;
    public CategoryAdapter(Context context, ArrayList<Category> categories,ArrayList<ItunesApp> lstItemsApp) {
        super(context, 0, categories);
        this.lstItemsApp=lstItemsApp;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category category = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_item, parent, false);
        }
        TextView txtCategory = (TextView) convertView.findViewById(R.id.txtCategory);
        ImageView img1=(ImageView)convertView.findViewById(R.id.imgCategory1);
        ImageView img2=(ImageView)convertView.findViewById(R.id.imgCategory2);
        ImageView img3=(ImageView)convertView.findViewById(R.id.imgCategory3);
        ImageView img4=(ImageView)convertView.findViewById(R.id.imgCategory4);
        ImageView img5=(ImageView)convertView.findViewById(R.id.imgCategory5);
        img1.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        img3.setVisibility(View.GONE);
        img4.setVisibility(View.GONE);
        img5.setVisibility(View.GONE);

        txtCategory.setText(category.getCategoryName());
        int cont=1;
        for(int i =0;i<lstItemsApp.size();i++){
            if(lstItemsApp.get(i).getLabelCategory().equals(category.getCategoryName())){
                byte[] decodedString = Base64.decode(lstItemsApp.get(i).getImageLink(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                Resources res = getContext().getResources();

                RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, decodedByte);
                dr.setCornerRadius(Math.max(decodedByte.getWidth(), decodedByte.getHeight()) / 8.0f);
                switch (cont){
                    case 1:
                        img1.setVisibility(View.VISIBLE);
                        img1.setImageDrawable(dr);
                        break;
                    case 2:
                        img2.setVisibility(View.VISIBLE);
                        img2.setImageDrawable(dr);
                        break;
                    case 3:
                        img3.setVisibility(View.VISIBLE);
                        img3.setImageDrawable(dr);
                        break;
                    case 4:
                        img4.setVisibility(View.VISIBLE);
                        img4.setImageDrawable(dr);
                        break;
                    case 5:
                        img5.setVisibility(View.VISIBLE);
                        img5.setImageDrawable(dr);
                        break;
                }
                cont++;
            }
        }
        return convertView;
    }
}
