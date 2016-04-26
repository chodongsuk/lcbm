package kr.com.lcbm;

import kr.ds.handler.EventHandler;
import kr.ds.utils.DsObjectUtils;
import kr.ds.widget.ContentViewPager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016-03-21.
 */
public class WebActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private EventHandler mSavedata;


    //웹뷰 관련
    private static final String TAG = "WebActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final int FILECHOOSER_RESULTCODE = 2;
    public static final int INPUT_FILE_REQUEST_CODE = 1;
    public static final int AREA_RESULTCODE = 3;
    private WebView mWebView;
    private ProgressBar pb;
    private MyWebChromeClient mChromeClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedata = (EventHandler) getIntent().getParcelableExtra("data");
        setContentView(R.layout.activity_web);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mSavedata.getEd_name())){
                mToolbar.setTitle(mSavedata.getEd_name());
            }else{
                mToolbar.setTitle(getResources().getString(R.string.app_name));
            }
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initViews();
        hideWebView();
        hideProgress();
        configureOfflineWebView(savedInstanceState);

    }

    private void initViews() {
        pb = (ProgressBar) findViewById(R.id.progress_bar);
        mWebView = (WebView) findViewById(R.id.webview);
    }

    private void configureOfflineWebView(Bundle savedInstanceState) {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setDomStorageEnabled(true);

// Set cache size to 8 mb by default. should be more than enough
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);

        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        String userAgent = mWebView.getSettings().getUserAgentString();
        mChromeClient = new MyWebChromeClient(this);
        mWebView.setWebChromeClient(mChromeClient);
        mWebView.setWebViewClient(new OfflineWebViewClient(this));

        if (savedInstanceState == null) {
            if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mSavedata.getEd_subject())) {
                loadURL(mSavedata.getEd_subject());
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadURL(String url) {
        mWebView.loadUrl(url);
    }


    public void showProgress(){
        pb.setVisibility(View.VISIBLE);
    }

    public void hideProgress(){
        pb.setVisibility(View.GONE);
    }

    public void showWebView(){
        mWebView.setVisibility(View.VISIBLE);
    }

    public void hideWebView(){
        mWebView.setVisibility(View.GONE);
    }



}
