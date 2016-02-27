package materialtest.jufequinta.navigationdrawer.Controller.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import materialtest.jufequinta.navigationdrawer.Controller.Activities.AppDetailActivity;
import materialtest.jufequinta.navigationdrawer.Controller.Activities.ItemDetailActivity;
import materialtest.jufequinta.navigationdrawer.Controller.Activities.MainActivity;
import materialtest.jufequinta.navigationdrawer.Controller.Adapters.ListItemAdapter;
import materialtest.jufequinta.navigationdrawer.Message.Entities.Category;
import materialtest.jufequinta.navigationdrawer.Message.Entities.ItunesApp;
import materialtest.jufequinta.navigationdrawer.R;

/**
 * Created by acer on 27/02/2016.
 */
public class ListAppFragment extends Fragment {
    ListView lstApps;
    ArrayList<Category> arrayCategory;
    ArrayList<ItunesApp> arrayItunesApp;

    public ListAppFragment(){
    }

    public ListAppFragment(ArrayList<ItunesApp> arrayItunesApp){
        this.arrayItunesApp = arrayItunesApp;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_list_app, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Lista de aplicaciones");

        lstApps = (ListView) view.findViewById(R.id.lstApps);
        ListItemAdapter adapter = new ListItemAdapter(this.getContext(),arrayItunesApp);
        lstApps.setDivider(null);
        lstApps.setAdapter(adapter);

        lstApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItunesApp itunesApp = arrayItunesApp.get(position);

                Bundle extra = new Bundle();
                extra.putSerializable("appDetail", itunesApp);

                Intent intent = new Intent(getActivity(), AppDetailActivity.class);
                intent.putExtra("extra",extra);
                startActivity(intent);
            }
        });

        return view;
    }
}
