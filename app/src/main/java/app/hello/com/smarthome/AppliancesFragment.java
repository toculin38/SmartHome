package app.hello.com.smarthome;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by 邱偉 on 2016/1/17.
 */
public class AppliancesFragment extends PlaceholderFragment {
    private String[] Applianceslist = {"電視", "電扇", "空調", "電燈"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        /*set up ListView*/
        setListView();
        return rootView;
    }

    private ListView setListView() {
        ListView listView = (ListView) rootView.findViewById(R.id.appliancesListView);
        ListAdapter listAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, Applianceslist);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*change Fragment*/
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, StatesFragment.newInstance(position + 1))
                        .addToBackStack(null)
                        .commit();
            }
        });
        return listView;
    }
}
