package app.hello.com.smarthome;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by User on 2016/11/17.
 */
public class CameraFragment extends PlaceholderFragment{

    private static String WebURL = "http://192.168.3.101:8080";
    private Handler m_Handler = new Handler();

    WebView myBrowser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        myBrowser = (WebView)(rootView.findViewById(R.id.webView));
        myBrowser.setInitialScale(140);

        WebSettings websettings = myBrowser.getSettings();    //取得網頁相關設定
        websettings.setSupportZoom(true);
        websettings.setBuiltInZoomControls(false);               //是否支持手指縮放
        websettings.setDisplayZoomControls(false);               //是否顯示縮放按鈕(內置縮放+/-按鈕)
        websettings.setJavaScriptEnabled(true);                 //是否支持網頁裡的JavaScript語法
        myBrowser.setWebViewClient(new WebViewClient());
        m_Handler.postDelayed(PrintView,0);
        return rootView;
    }

    private Runnable PrintView = new Runnable()
    {
        public void run()
        {
            RefreshImage();
        }
        private void RefreshImage()
        {
            myBrowser.loadUrl(WebURL);
        }
    };



}
