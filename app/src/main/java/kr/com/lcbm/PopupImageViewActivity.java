package kr.com.lcbm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import kr.ds.adapter.PopupImageViewPagerAdapter;
import kr.ds.utils.DsObjectUtils;
import kr.ds.widget.ImageViewPager;
import kr.ds.widget.ViewpagerNavibar;

public class PopupImageViewActivity extends FragmentActivity implements OnClickListener{
	private Fragment mFragment;
	private ImageViewPager mPopupViewPager;
	private LinearLayout mLinearLayoutNavibar;
	private ViewpagerNavibar mViewpagerNavibar;
	private String mUrls;
	private Bundle mBundle;
	private int mPosition = 0;
	private PopupImageViewPagerAdapter mPopupImageViewPagerAdapter;
	private ImageView mImageViewClose;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState != null){
			mUrls = savedInstanceState.getString("urls");
			mPosition = savedInstanceState.getInt("position");
		}else{
			mBundle = getIntent().getExtras();
			mUrls = mBundle.getString("urls");
			mPosition = mBundle.getInt("position");
		}
		setContentView(R.layout.popup_imageview);
		mLinearLayoutNavibar = (LinearLayout)findViewById(R.id.linearLayoutNavibar);
		(mImageViewClose = (ImageView)findViewById(R.id.imageView_close)).setOnClickListener(this);
		mPopupViewPager = (ImageViewPager)findViewById(R.id.viewPager_popup);
		mPopupViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				mViewpagerNavibar.setButton(arg0);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
		if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mUrls)){
			String[] images = mUrls.split(",");
			mPopupImageViewPagerAdapter = new PopupImageViewPagerAdapter(getSupportFragmentManager(), images);
			mPopupViewPager.setAdapter(mPopupImageViewPagerAdapter);
			
			mViewpagerNavibar = new ViewpagerNavibar(getApplicationContext(), images.length);
			mLinearLayoutNavibar.addView(mViewpagerNavibar);
			mPopupViewPager.setCurrentItem(mPosition);
			mViewpagerNavibar.setButton(mPosition);
		}
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("urls", mUrls);
		outState.putInt("position", mPosition);
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageView_close:
			finish();
			break;
		}
	}

}
