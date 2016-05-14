package app.hello.com.smarthome;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
/**
 * Created by 邱偉 on 2016/1/19.
 */

public class TVControllerFragment extends ControllerFragment{

    private static float availableDistance = 50;
    private float distance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setAllButtonListener((ViewGroup) rootView);
        distance = ((MainActivity)getActivity()).getDistance();
        return rootView;
    }
    private void setAllButtonListener(ViewGroup viewGroup){
        View v;
        for(int i = 0; i < viewGroup.getChildCount(); i++) {
            v = viewGroup.getChildAt(i);
            if (v instanceof ViewGroup) {
                setAllButtonListener((ViewGroup)v);
            } else if (v instanceof Button) v.setOnClickListener(listener);
        }
    }
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
