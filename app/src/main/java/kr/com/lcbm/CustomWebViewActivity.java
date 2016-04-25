package kr.com.lcbm;

import kr.ds.handler.CategoryHandler;
import kr.ds.utils.DsObjectUtils;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomWebViewActivity extends BaseActivity {
	private CategoryHandler mSavedata;
	private Bundle mBundle;
	private String mobliehomepage, name;
	private WebView mWebView;
	private LinearLayout ProgressArea;
	private TextView mTextViewName;
	private Toolbar mToolbar;


	public static final String INTENT_PROTOCOL_START = "intent:";
	public static final String INTENT_PROTOCOL_INTENT = "#Intent;";
	public static final String INTENT_PROTOCOL_END = ";end;";
	public static final String GOOGLE_PLAY_STORE_PREFIX = "market://details?id=";



	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null){
			mSavedata = (CategoryHandler) savedInstanceState.getParcelable("data");
			mobliehomepage = mSavedata.getHomepage();
			name = mSavedata.getName();

		}else{
			mBundle = getIntent().getExtras();
			mSavedata = (CategoryHandler) mBundle.getParcelable("data");
			mobliehomepage = mSavedata.getHomepage();
			name = mSavedata.getName();
		}
		
		setContentView(R.layout.web);
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		if (mToolbar != null) {
			if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(name)){
				mToolbar.setTitle(name);
			}
	    	setSupportActionBar(mToolbar);
	    	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    }

		mWebView = (WebView) findViewById(R.id.web_homepage);
		ProgressArea = (LinearLayout) findViewById(R.id.progress_area);
		
	
		try {
			if(mobliehomepage != null){
				if(!mobliehomepage.matches("")){
					getWebView(mobliehomepage);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putParcelable("data", mSavedata);
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
				//WebHomepage.setInitialScale(getScale());
				//WebHomepage.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
				mWebView.loadUrl(url);
				mWebView.setWebViewClient(new WebViewClients());

				mWebView.setWebChromeClient(new WebChromeClient() {
					@Override
					public boolean onJsAlert(WebView view, String url,
							String message, final JsResult result) {
						new AlertDialog.Builder(CustomWebViewActivity.this)
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
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

	private class WebViewClients extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.startsWith("tel:")) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
				startActivity(intent);
				return true;
			}else if (url.startsWith("mailto:")) {
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
				startActivity(intent);
				return true;
			}else if (url.startsWith("sms:")) {
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
				startActivity(intent);
				return true;
			}else if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:") ) {
				if (android.os.Build.VERSION.SDK_INT >= 19) {
					if (url.startsWith(INTENT_PROTOCOL_START)) {
						final int customUrlStartIndex = INTENT_PROTOCOL_START.length();
						final int customUrlEndIndex = url.indexOf(INTENT_PROTOCOL_INTENT);
						if (customUrlEndIndex < 0) {
							return false;
						} else {
							//intent 빼고 보내기.
							final String customUrl = url.substring(customUrlStartIndex, customUrlEndIndex);
							Log.i("TEST_customUrl", customUrl + "");
							Intent intent = new Intent(Intent.ACTION_VIEW);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							try {
								intent.setData(Uri.parse(customUrl));
								getBaseContext().startActivity(intent);
							} catch (ActivityNotFoundException e) {
								final int packageStartIndex = customUrlEndIndex+ INTENT_PROTOCOL_INTENT.length();
								final int packageEndIndex = url.indexOf(INTENT_PROTOCOL_END);

								final String packageName = url.substring(packageStartIndex,	packageEndIndex < 0 ? url.length()	: packageEndIndex);
								intent.setData(Uri.parse(GOOGLE_PLAY_STORE_PREFIX	+ packageName));
								getBaseContext().startActivity( intent );
							}
							return true;
						}
					}else{
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
						startActivity(intent);
					}
				}else{
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(intent);
				}
				return true;
			}else{
				mWebView.loadUrl(url);
			}
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
	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (KeyCode == KeyEvent.KEYCODE_BACK) {
				if(mWebView.canGoBack()){
					mWebView.goBack();
				}else{
					finish();
				}
				return true;
			}
		}
		return super.onKeyDown(KeyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
            finish();
            return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
