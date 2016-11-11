package app.hello.com.smarthome;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by 邱偉 on 2016/1/15.
 */
public class ControllerFragment extends AppliancesFragment {
    private static final String ARG_LIST_NUMBER = "list_number";

    public static ControllerFragment newInstance(int listNumber) {
        ControllerFragment fragment = CreateFragmentByListNumber(listNumber);
        Bundle args = new Bundle();//bundle?
        args.putInt(ARG_LIST_NUMBER, listNumber);
        fragment.setArguments(args);
        return fragment;
    }
    private static ControllerFragment CreateFragmentByListNumber(int listNumber) {
        ControllerFragment fragment;
        switch (listNumber) {
            case 1:
            case 2:
            case 3:
            case 4:
            default:
                fragment = new ControllerFragment();
        }
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        switch (getArguments().getInt(ARG_LIST_NUMBER)) {
            case 1:
                rootView = inflater.inflate(R.layout.fragment_controller_tv, container, false);
                break;
            case 2:
                rootView = inflater.inflate(R.layout.fragment_controller_fan, container, false);
                break;
            case 3:
                rootView = inflater.inflate(R.layout.fragment_controller_air, container, false);
                break;
            case 4:
                rootView = inflater.inflate(R.layout.fragment_controller_light, container, false);
                break;
            default:
                rootView = inflater.inflate(R.layout.fragment_smart_home, container, false);
        }

        setAllButtonListener((ViewGroup) rootView);
        distance = ((MainActivity)getActivity()).getDistance();

        return rootView;
    }

    protected void setAllButtonListener(ViewGroup viewGroup){
        View v;
        for(int i = 0; i < viewGroup.getChildCount(); i++) {
            v = viewGroup.getChildAt(i);
            if (v instanceof ViewGroup) {
                setAllButtonListener((ViewGroup)v);
            } else if (v instanceof Button) v.setOnClickListener(listener);
        }
    }

    private static float availableDistance = 50;
    private float distance;

    private Button.OnClickListener listener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(!commandManager.isConnecting()){
                new AlertDialog.Builder(getContext())
                        .setTitle("錯誤")
                        .setMessage("尚未連接至主機")
                        .show();
            }
            else if(distance > availableDistance){
                new AlertDialog.Builder(getContext())
                        .setTitle("錯誤")
                        .setMessage("距離過遠不允許控制")
                        .show();
            }
            else{
                commandManager.sendCommand(v.getTag().toString());
            }
        }
    };

}
