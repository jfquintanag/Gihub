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

import materialtest.jufequinta.navigationdrawer.Controller.Activities.ItemDetailActivity;
import materialtest.jufequinta.navigationdrawer.Controller.Activities.MainActivity;
import materialtest.jufequinta.navigationdrawer.Controller.Adapters.CategoryAdapter;
import materialtest.jufequinta.navigationdrawer.Message.Entities.Category;
import materialtest.jufequinta.navigationdrawer.Message.Entities.ItunesApp;
import materialtest.jufequinta.navigationdrawer.R;

public class CategoryFragment extends Fragment {
    ListView lstCategory;
    ArrayList<Category> arrayCategory;
    ArrayList<ItunesApp> arrayItunesApp;

    public CategoryFragment(){
    }

    public CategoryFragment(ArrayList<Category> arrayCategory, ArrayList<ItunesApp> arrayItunesApp){
        this.arrayCategory = arrayCategory;
        this.arrayItunesApp = arrayItunesApp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_category, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Lista de categorías");

        lstCategory = (ListView) view.findViewById(R.id.lstCategory);
        CategoryAdapter adapter = new CategoryAdapter(this.getContext(), arrayCategory,arrayItunesApp);
        lstCategory.setDivider(null);
        lstCategory.setAdapter(adapter);

        lstCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = arrayCategory.get(position);
                ArrayList<ItunesApp> lstItunesAppTMP = new ArrayList<ItunesApp>();
                for (int i = 0; i < arrayItunesApp.size(); i++) {
                    if (arrayItunesApp.get(i).getLabelCategory().equals(category.getCategoryName())) {
                        lstItunesAppTMP.add(arrayItunesApp.get(i));
                    }
                }

                Bundle extra = new Bundle();
                extra.putSerializable("objects", lstItunesAppTMP);

                Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
                intent.putExtra("extra",extra);
                startActivity(intent);
            }
        });

        return view;
    }
}