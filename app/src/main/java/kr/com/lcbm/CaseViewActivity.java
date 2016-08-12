package kr.com.lcbm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.ContentHandler;

import kr.ds.handler.CategoryHandler;
import kr.ds.handler.ListHandler;
import kr.ds.utils.DsObjectUtils;

/**
 * Created by Administrator on 2016-08-12.
 */
public class CaseViewActivity extends BaseActivity {

    private Toolbar mToolbar;
    private ListHandler mSavedata;
    private WebView mWebView;
    private LinearLayout ProgressArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSavedata = (ListHandler) getIntent().getParcelableExtra("data");
        setContentView(R.layout.activity_case_view);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(mSavedata.getGd_name());
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mWebView = (WebView) findViewById(R.id.webview);
        ProgressArea = (LinearLayout) findViewById(R.id.progress_area);
        getWebView("http://lcbm.cafe24.com/json/caseview.php?gd_uid="+mSavedata.getGd_uid());
    }

    public void getWebView(final String url) {
        final Handler handler = new Handler();
        Runnable doInit = new Runnable() {
            public void run() {
                mWebView.getSettings().setLoadWithOverviewMode(true);// 축소된상태
                mWebView.getSettings().setUseWideViewPort(true); // Web페이지사이즈
                mWebView.setVerticalScrollbarOverlay(true);
                mWebView.getSettings().setSupportZoom(true);// 줌컨트롤
                mWebView.getSettings().setBuiltInZoomControls(true);
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                mWebView.getSettings().setSaveFormData(true);
                mWebView.getSettings().setSavePassword(true);
                //mWebView.setInitialScale(getScale());
                //mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
                mWebView.loadUrl(url);
                mWebView.setWebViewClient(new WebViewClients());

                mWebView.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public boolean onJsAlert(WebView view, String url,
                                             String message, final JsResult result) {
                        new AlertDialog.Builder(CaseViewActivity.this)
                                .setMessage(message)
                                .setPositiveButton(android.R.string.ok,
                                        new AlertDialog.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                result.confirm();
                                            }
                                        }).setCancelable(false).create().show();
                        return true;

                    };
                });
            }
        };
        ProgressArea.setVisibility(View.VISIBLE);
        handler.postDelayed(doInit, 0);
    }

    private class WebViewClients extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            ProgressArea.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            ProgressArea.setVisibility(View.GONE);
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
}
