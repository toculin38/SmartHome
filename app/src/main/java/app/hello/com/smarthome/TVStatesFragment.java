package app.hello.com.smarthome;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by 邱偉 on 2016/3/5.
 */
public class TVStatesFragment extends StatesFragment {
    private static float availableDistance = 500;
    private Button controlButton;
    private TextView power;
    private TextView channel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        controlButton = (Button)(rootView.findViewById(R.id.controlBTN));
        power = (TextView)(rootView.findViewById(R.id.powerTXV));
        channel = (TextView)(rootView.findViewById(R.id.channelTXV));
        controlButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*change Fragment*/
                if(!commandManager.isConnecting()){
                    new AlertDialog.Builder(getContext())
                            .setTitle("錯誤")
                            .setMessage("尚未連接至主機")
                            .show();
                }
                else if(((MainActivity)getActivity()).getDistance() > availableDistance){
                    new AlertDialog.Builder(getContext())
                            .setTitle("錯誤")
                            .setMessage("距離過遠不允許控制")
                            .show();

                }
                else{
                    commandManager.sendCommand("TV");
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, ControllerFragment.newInstance(1))
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        getStates();
        return rootView;
    }

    public void getStates(){
        commandManager.sendCommand("get TV state");
        power.setText(commandManager.getData());
        channel.setText(commandManager.getData());
    }
}
