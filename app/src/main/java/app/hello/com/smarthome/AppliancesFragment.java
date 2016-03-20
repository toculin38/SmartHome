package app.hello.com.smarthome;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
    private void setListView() {
        ListView listView = (ListView) rootView.findViewById(R.id.appliancesListView);
        ListAdapter listAdapter = new ArrayAdapter(getContext(),R.layout.appliances_list_textview, Applianceslist);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new ItemClickListener());
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener{
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*change Fragment*/

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, StatesFragment.newInstance(position + 1))
                    .addToBackStack(null)
                    .commit();
        }
    }
}
