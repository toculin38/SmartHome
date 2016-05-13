package app.hello.com.smarthome;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks,LocationListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private CommandManager commandManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        commandManager = new CommandManager();
        testLocationProvider();		//檢查定位服務
    }



    private LocationManager lms;
    private String bestProvider;	//最佳資訊提供者
    private Location location;
    private static double homeLongitude = 120.6673807;
    private static double homeLatitude = 24.1163121;

    public float getDistance(){
        if(location == null)
                return 99999;
        float distance[] = new float[2];
        Double longitude = location.getLongitude();	//取得經度
        Double latitude = location.getLatitude();	//取得緯度
        Location.distanceBetween(longitude, latitude, homeLongitude, homeLatitude,distance);
        return distance[0];
    }

    private void testLocationProvider() {
        //取得系統定位服務
        LocationManager status = (LocationManager) (this.getSystemService(Context.LOCATION_SERVICE));
        if (status.isProviderEnabled(LocationManager.GPS_PROVIDER) || status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //如果GPS或網路定位開啟，呼叫locationServiceInitial()更新位置
            locationServiceInitial();
        } else {
            Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));	//開啟設定頁面
        }
    }

    private void locationServiceInitial() {
        lms = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);	//取得系統定位服務
        Criteria criteria = new Criteria();	//資訊提供者選取標準
        try {
            List<String> providers = lms.getProviders(criteria,true);
            for (String provider : providers) {
                location = lms.getLastKnownLocation(provider);
                if (location == null) {
                    continue;
                } else {
                    bestProvider = provider;
                }
            }
            if(bestProvider != null) {
                lms.requestLocationUpdates(bestProvider, 1000, 0, this);
                location = lms.getLastKnownLocation(bestProvider);
            }
            else{
                Toast.makeText(this, "找不到GPS提供者，請稍後重新啟動後再試", Toast.LENGTH_LONG).show();
            }
        } catch (SecurityException e) {
            System.out.print("...");
        }
    }

    public Location getLocation() {
        return location;
    }

    public String getAddressByLocation(Location location) {
        String returnAddress = "";
        try {
            if (location != null) {
                Double longitude = location.getLongitude();	//取得經度
                Double latitude = location.getLatitude();	//取得緯度
                //建立Geocoder物件: Android 8 以上模疑器測式會失敗
                Geocoder gc = new Geocoder(this, Locale.TRADITIONAL_CHINESE);	//地區:台灣
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

    @Override
    public void onLocationChanged(Location location) {	//當地點改變時
        try {
            this.location = location;
        } catch (SecurityException e) {
            Toast.makeText(this, "座標無法更新成功", Toast.LENGTH_LONG).show();
        }
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

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.smart_home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                Toast.makeText(this, "Settings action", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about:
                Toast.makeText(this, "about action", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /*20160116*/
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (mNavigationDrawerFragment.isDrawerOpen())
                super.onBackPressed();
            else
                mNavigationDrawerFragment.openDrawer();
        }
        else
            super.onBackPressed();
    }
    /*20160117*/
    public  CommandManager getCommandManager(){
        return this.commandManager;
    }
}
