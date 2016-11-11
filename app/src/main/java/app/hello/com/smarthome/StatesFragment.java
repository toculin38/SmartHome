package app.hello.com.smarthome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by 邱偉 on 2016/1/15.
 */
public class StatesFragment extends AppliancesFragment {
    private static final String ARG_LIST_NUMBER = "list_number";

    public static StatesFragment newInstance(int listNumber) {
        StatesFragment fragment = CreateFragmentByListNumber(listNumber);
        Bundle args = new Bundle();//bundle?
        args.putInt(ARG_LIST_NUMBER, listNumber);
        fragment.setArguments(args);
        return fragment;
    }
    private static StatesFragment CreateFragmentByListNumber(int listNumber) {
        StatesFragment fragment;
        switch (listNumber) {
            case 1:
                fragment = new TVStatesFragment();
                break;
            case 2:
                fragment = new FanStatesFragment();
                break;
            case 3:
                fragment = new AirStatesFragment();
                break;
            case 4:
                fragment = new LightStatesFragment();
                break;
            default:
                fragment = new StatesFragment();
        }
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        switch (getArguments().getInt(ARG_LIST_NUMBER)) {
            case 1:
                rootView = inflater.inflate(R.layout.fragment_states_tv, container, false);
                break;
            case 2:
                rootView = inflater.inflate(R.layout.fragment_states_fan, container, false);
                break;
            case 3:
                rootView = inflater.inflate(R.layout.fragment_states_air, container, false);
                break;
            case 4:
                rootView = inflater.inflate(R.layout.fragment_states_light, container, false);
                break;
            default:
                rootView = inflater.inflate(R.layout.fragment_smart_home, container, false);
        }

        return rootView;
    }

}


