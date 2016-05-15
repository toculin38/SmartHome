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
    private Button controlButton;
    private TextView enable;
    private TextView power;
    private TextView channel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        controlButton = (Button)(rootView.findViewById(R.id.controlBTN));
        enable = (TextView)(rootView.findViewById(R.id.enableTXV));
        power = (TextView)(rootView.findViewById(R.id.powerTXV));
        channel = (TextView)(rootView.findViewById(R.id.channelTXV));
        controlButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*change Fragment*/
                commandManager.sendCommand("TV");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ControllerFragment.newInstance(1))
                        .addToBackStack(null)
                        .commit();
            }
        });
        getStates();
        return rootView;
    }

    public void getStates(){
        commandManager.sendCommand("get TV state");

        if(Boolean.parseBoolean(commandManager.getData())){
            enable.setText("啟用");
        }else{
            enable.setText("註銷");
        }

        if(Boolean.parseBoolean(commandManager.getData())){
            power.setText("開啟");
        }else{
            power.setText("關閉");
        }

        channel.setText(commandManager.getData());
    }
}
