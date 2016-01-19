package app.hello.com.smarthome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 邱偉 on 2016/1/15.
 */
public class ControllerFragment extends AppliancesFragment {
    private static final String ARG_LIST_NUMBER = "list_number";

    public static ControllerFragment newInstance(int listNumber) {
        ControllerFragment fragment = new ControllerFragment();
        Bundle args = new Bundle();//bundle?
        args.putInt(ARG_LIST_NUMBER, listNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        switch (getArguments().getInt(ARG_LIST_NUMBER)) {
            case 1:
                rootView = inflater.inflate(R.layout.fragment_controller_tv, container, false);
                break;
            default:
                rootView = inflater.inflate(R.layout.fragment_smart_home, container, false);
        }
        return rootView;
    }

}
