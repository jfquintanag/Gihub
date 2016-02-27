package materialtest.jufequinta.navigationdrawer.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import materialtest.jufequinta.navigationdrawer.R;

/**
 * Created by acer on 27/02/2016.
 */
public class ListMenuAdapter  extends ArrayAdapter<String> {
    public ListMenuAdapter(Context context, ArrayList<String> menuItems) {
        super(context, 0, menuItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String menuItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_menu_item, parent, false);
        }
        TextView txtViewLabel = (TextView) convertView.findViewById(R.id.grdItemLabel);
        TextView txtViewDesc = (TextView) convertView.findViewById(R.id.grdItemLabel);
        txtViewLabel.setText(menuItem);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_item_image);

        if (menuItem.equals("Categorías")) {
            imageView.setImageResource(R.drawable.category_item);
            txtViewDesc.setText("Categorías.");
        } else if (menuItem.equals("Aplicaciones")) {
            imageView.setImageResource(R.drawable.app_menu_item);
            txtViewDesc.setText("Listado de las apps.");
        }
        return convertView;
    }
}
