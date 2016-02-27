package materialtest.jufequinta.navigationdrawer.Controller.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import materialtest.jufequinta.navigationdrawer.Controller.Activities.MainActivity;
import materialtest.jufequinta.navigationdrawer.Controller.Adapters.GridMenuAdapter;
import materialtest.jufequinta.navigationdrawer.Message.Entities.Category;
import materialtest.jufequinta.navigationdrawer.Message.Entities.ItunesApp;
import materialtest.jufequinta.navigationdrawer.R;

/**
 * Created by acer on 27/02/2016.
 */
public class MenuListFragment  extends Fragment {
    ArrayList<Category> arrayCategory;
    ArrayList<ItunesApp> arrayItunesApp;
    ListView lstPpal;
    View view;

    static final String[] ITEM_MENU_GRID = new String[] {
            "Categorías", "Aplicaciones"};

    public MenuListFragment() {
    }

    public MenuListFragment(ArrayList<Category> arrayCategory, ArrayList<ItunesApp> arrayItunesApp) {
        this.arrayCategory = arrayCategory;
        this.arrayItunesApp = arrayItunesApp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_menu, container, false);

        lstPpal = (ListView) view.findViewById(R.id.lstMenu);
        lstPpal.setAdapter(new GridMenuAdapter(view.getContext(),ITEM_MENU_GRID));
        lstPpal.setDivider(null);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Menu principal");

        lstPpal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                RemplaceFragment(position);
            }
        });

        return  view;
    }

    public void RemplaceFragment(int option){
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (option){
            case 0:
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_up,R.anim.slide_down);
                CategoryFragment inboxFragment = new CategoryFragment(arrayCategory, arrayItunesApp);
                fragmentTransaction.replace(R.id.fragment, inboxFragment);
                fragmentTransaction.commit();
                break;

            case 1:
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_up,R.anim.slide_down);
                ListAppFragment listAppFragment = new ListAppFragment(arrayItunesApp);
                fragmentTransaction.replace(R.id.fragment, listAppFragment);
                fragmentTransaction.commit();
                break;
        }

    }
}
