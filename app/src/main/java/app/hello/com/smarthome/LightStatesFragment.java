package app.hello.com.smarthome;

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
public class LightStatesFragment extends StatesFragment {
    private Button controlButton;
    private TextView enable;
    private TextView type;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        controlButton = (Button)(rootView.findViewById(R.id.controlBTN));
        enable = (TextView)(rootView.findViewById(R.id.enableTXV));
        type = (TextView)(rootView.findViewById(R.id.typeTXV));
        controlButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*change Fragment*/
                commandManager.sendCommand("Light");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ControllerFragment.newInstance(4))
                        .addToBackStack(null)
                        .commit();
            }
        });
        getStates();
        return rootView;
    }

    public void getStates(){
        commandManager.sendCommand("get Light state");

        if(Boolean.parseBoolean(commandManager.getData())){
            enable.setText("啟用");
        }else{
            enable.setText("註銷");
        }

        try{
            String s = commandManager.getData();
            if(s.equals("1")){
                type.setText("白燈");
            }else if(s.equals("2")) {
                type.setText("黃燈");
            }else{
                type.setText("關閉中");
            }
        }catch(NullPointerException e) {
            type.setText("關閉中");
        }

    }
}

