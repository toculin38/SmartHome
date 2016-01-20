package app.hello.com.smarthome;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.TimerTask;

/**
 * Created by 邱偉 on 2016/1/17.
 */
public class ConnectionFragment extends PlaceholderFragment {
    private Button connectButton;
    private EditText IPAddressEditText;
    private TextView stateTextview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        /*set up Textview*/
        stateTextview = (TextView)(rootView.findViewById(R.id.statTXV));
        /*set up EditText*/
        IPAddressEditText = (EditText) (rootView.findViewById(R.id.addressEDT));
        setInputFilter(); /*設定輸入格式*/
        IPAddressEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() { /*EditText中按下Enter鍵時的行為*/
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                connectToServer();
                return true;
            }
        });
        /*set up Button*/
        connectButton = (Button) rootView.findViewById(R.id.connectBtn);
        connectButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToServer();
            }
        });
        rootView.post(stateCheck);
        return rootView;
    }

    private void setInputFilter() {
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       android.text.Spanned dest, int dstart, int dend) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dstart)
                            + source.subSequence(start, end)
                            + destTxt.substring(dend);
                    if (!resultingTxt
                            .matches("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (int i = 0; i < splits.length; i++) {
                            if (Integer.valueOf(splits[i]) > 255) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }
        };
        IPAddressEditText.setFilters(filters);
    }

    public void connectToServer(){
        if(commandManager.isConnecting()){
            Toast.makeText(getActivity(), "已於連線狀態！", Toast.LENGTH_LONG).show();
        }else{
            String address = IPAddressEditText.getText().toString();
            Toast.makeText(getActivity(), address + " 連線中 請稍後...", Toast.LENGTH_LONG).show();
            commandManager.connectToServer(address);
            if(commandManager.isConnecting())
                Toast.makeText(getActivity(), address + " 連接成功！", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getActivity(), address + " 連接失敗！", Toast.LENGTH_LONG).show();
        }
    }

    private TimerTask stateCheck = (new TimerTask() {
        @Override
        public void run() {
            if(commandManager.isConnecting()){
                stateTextview.setText("已連線");
                stateTextview.setTextColor(Color.GREEN);
            }else{
                stateTextview.setText("未連線");
                stateTextview.setTextColor(Color.RED);
            }
            rootView.postDelayed(stateCheck, 1000);
        }
    });
}
