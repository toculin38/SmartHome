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
public class LocationFragment extends PlaceholderFragment implements LocationListener {
    /** Called when the activity is first created. */
    private boolean getService = false;		//是否已開啟定位服務
    private TextView longitude_txt;
    private TextView latitude_txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        longitude_txt = (TextView)rootView.findViewById(R.id.longitude);
        latitude_txt = (TextView)rootView.findViewById(R.id.latitude);
        testLocationProvider();		//檢查定位服務
        return rootView;
    }

    private void testLocationProvider() {
        //取得系統定位服務
        LocationManager status = (LocationManager) (getActivity().getSystemService(Context.LOCATION_SERVICE));
        if (status.isProviderEnabled(LocationManager.GPS_PROVIDER) || status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //如果GPS或網路定位開啟，呼叫locationServiceInitial()更新位置
            getService = true;	//確認開啟定位服務
            locationServiceInitial();
        } else {
            Toast.makeText(getActivity(), "請開啟定位服務", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));	//開啟設定頁面
        }
    }

    private LocationManager lms;
    private String bestProvider = LocationManager.GPS_PROVIDER;	//最佳資訊提供者
    private void locationServiceInitial() {
        lms = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);	//取得系統定位服務
        Criteria criteria = new Criteria();	//資訊提供者選取標準
        bestProvider = lms.getBestProvider(criteria, true);	//選擇精準度最高的提供者
        try {
            Location location = lms.getLastKnownLocation(bestProvider);
            getLocation(location);
        } catch (SecurityException e) {
            System.out.print("...");
        }
    }

    private void getLocation(Location location) {	//將定位資訊顯示在畫面中
        if(location != null) {
            Double longitude = location.getLongitude();	//取得經度
            Double latitude = location.getLatitude();	//取得緯度
            longitude_txt.setText(longitude_txt.getText()+String.valueOf(longitude));
            latitude_txt.setText(latitude_txt.getText()+String.valueOf(latitude));
        }
        else {
            Toast.makeText(getActivity(), "無法定位座標", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {	//當地點改變時
        getLocation(location);
    }

    @Override
    public void onProviderDisabled(String arg0) {	//當GPS或網路定位功能關閉時
    }

    @Override
    public void onProviderEnabled(String arg0) {	//當GPS或網路定位功能開啟
    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {	//定位狀態改變
    }
    public String getAddressByLocation(Location location) {
        String returnAddress = "";
        try {
            if (location != null) {
                Double longitude = location.getLongitude();	//取得經度
                Double latitude = location.getLatitude();	//取得緯度

                //建立Geocoder物件: Android 8 以上模疑器測式會失敗
                Geocoder gc = new Geocoder(getActivity(), Locale.TRADITIONAL_CHINESE);	//地區:台灣
                //自經緯度取得地址
                List<Address> lstAddress = gc.getFromLocation(latitude, longitude, 1);

                //	if (!Geocoder.isPresent()){ //Since: API Level 9
                //		returnAddress = "Sorry! Geocoder service not Present.";
                //	}
                returnAddress = lstAddress.get(0).getAddressLine(0);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return returnAddress;
    }
}
