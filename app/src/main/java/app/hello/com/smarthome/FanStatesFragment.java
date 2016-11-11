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
public class FanStatesFragment extends StatesFragment {
    private Button controlButton;
    private TextView enable;
    private TextView speed;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        controlButton = (Button)(rootView.findViewById(R.id.controlBTN));
        enable = (TextView)(rootView.findViewById(R.id.enableTXV));
        speed = (TextView)(rootView.findViewById(R.id.speedTXV));
        controlButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*change Fragment*/
                commandManager.sendCommand("Fan");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ControllerFragment.newInstance(2))
                        .addToBackStack(null)
                        .commit();
            }
        });
        getStates();
        return rootView;
    }

    public void getStates(){
        commandManager.sendCommand("get Fan state");

        if(Boolean.parseBoolean(commandManager.getData())){
            enable.setText("啟用");
        }else{
            enable.setText("註銷");
        }

        try{
            String s = commandManager.getData();
            if(s.equals("0")){
                speed.setText("關閉中");
            }else if(s.equals("1")) {
                speed.setText("弱風");
            }else if(s.equals("2")){
                speed.setText("中等");
            }else if(s.equals("3")){
                speed.setText("強風");
            }else
                speed.setText("關閉中");
        }catch(NullPointerException e) {
            speed.setText("關閉中");
        }

    }

}

