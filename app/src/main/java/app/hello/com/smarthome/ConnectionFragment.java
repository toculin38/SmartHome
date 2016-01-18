package app.hello.com.smarthome;

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

/**
 * Created by 邱偉 on 2016/1/17.
 */
public class ConnectionFragment extends PlaceholderFragment {
    private static int port = 8890;
    private Button connectButton;
    private EditText IPAddressEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        /*set up EditText*/
        IPAddressEditText = (EditText) (rootView.findViewById(R.id.EDTaddress));
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
        Toast.makeText(getActivity(), IPAddressEditText.getText() + " 連線中 請稍後...", Toast.LENGTH_SHORT).show();
        String address = IPAddressEditText.getText().toString();
        if(commandManager.connectToServer(address, port))
            Toast.makeText(getActivity(), IPAddressEditText.getText() + " 連接成功！", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), IPAddressEditText.getText() + " 連接失敗！", Toast.LENGTH_LONG).show();
    }
}
