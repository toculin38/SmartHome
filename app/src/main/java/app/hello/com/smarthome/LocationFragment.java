package app.hello.com.smarthome;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.List;
import java.util.Locale;
import android.location.Address;
import android.location.Criteria;
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
    private Location location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        longitude_txt = (TextView)rootView.findViewById(R.id.longitude);
        latitude_txt = (TextView)rootView.findViewById(R.id.latitude);
        distance_txt = (TextView)rootView.findViewById(R.id.distance);
        location = ((MainActivity)getActivity()).getLocation();
        setLocation();
        return rootView;
    }

    private void setLocation() {	//將定位資訊顯示在畫面中
        float distance = ((MainActivity)getActivity()).getDistance();
        if(location != null) {
            Double longitude = location.getLongitude();	//取得經度
            Double latitude = location.getLatitude();	//取得緯度
            longitude_txt.setText(longitude_txt.getText()+String.valueOf(longitude));
            latitude_txt.setText(latitude_txt.getText() + String.valueOf(latitude));
            distance_txt.setText(distance_txt.getText()+String.valueOf(distance));
        }
        else {
            Toast.makeText(getActivity(), "無法定位座標", Toast.LENGTH_LONG).show();
            distance_txt.setText(distance_txt.getText() + String.valueOf(distance));
        }
    }
}
