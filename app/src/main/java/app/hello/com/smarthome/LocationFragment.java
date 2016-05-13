package app.hello.com.smarthome;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.List;
import java.util.Locale;
import android.location.Address;
import android.location.Geocoder;
import android.widget.TextView;

/**
 * Created by 邱偉 on 2016/2/16.
 */
public class LocationFragment extends PlaceholderFragment {
    /** Called when the activity is first created. */


    private TextView longitude_txt;
    private TextView latitude_txt;
    private TextView distance_txt;
    private TextView address_txt;
    private Location location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        longitude_txt = (TextView)rootView.findViewById(R.id.longitude);
        latitude_txt = (TextView)rootView.findViewById(R.id.latitude);
        distance_txt = (TextView)rootView.findViewById(R.id.distance);
        address_txt = (TextView)rootView.findViewById(R.id.address);
        location = ((MainActivity)getActivity()).getLocation();
        setLocation();
        return rootView;
    }

    private void setLocation() {	//將定位資訊顯示在畫面中
        float distance = ((MainActivity)getActivity()).getDistance();
        if(location != null) {
            Double longitude = location.getLongitude();	//取得經度
            Double latitude = location.getLatitude();	//取得緯度
            String address = ((MainActivity) getActivity()).getAddressByLocation(location);
            longitude_txt.setText(longitude_txt.getText() + String.valueOf(longitude));
            latitude_txt.setText(latitude_txt.getText() + String.valueOf(latitude));
            distance_txt.setText(distance_txt.getText() + String.valueOf(distance));
            address_txt.setText(address);
        }
        else {
            Toast.makeText(getActivity(), "無法定位座標", Toast.LENGTH_LONG).show();
            distance_txt.setText(distance_txt.getText() + String.valueOf(distance));
        }
    }
}
