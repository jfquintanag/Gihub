package materialtest.jufequinta.navigationdrawer.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import materialtest.jufequinta.navigationdrawer.R;

/**
 * Created by acer on 27/02/2016.
 */
public class GridMenuAdapter extends BaseAdapter {
    private Context context;
    private final String[] menuItems;

    public GridMenuAdapter(Context context, String[] menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);
            gridView = inflater.inflate(R.layout.grid_menu_item, null);

            TextView txtViewLabel = (TextView) gridView.findViewById(R.id.grdItemLabel);
            TextView txtViewDesc = (TextView) gridView.findViewById(R.id.grdItemLabel);
            txtViewLabel.setText(menuItems[position]);

            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            String menuItem= menuItems[position];

            if (menuItem.equals("Categorías")) {
                imageView.setImageResource(R.drawable.category_item);
                txtViewDesc.setText("Categorías.");
            } else if (menuItem.equals("Aplicaciones")) {
                imageView.setImageResource(R.drawable.app_menu_item);
                txtViewDesc.setText("Listado de las apps.");
            }

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return menuItems.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
