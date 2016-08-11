package kr.ds.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import kr.ds.fragment.ImageViewFragment;

public class ImageViewPagerAdapter extends FragmentStatePagerAdapter {
	private String[] mImageUrl;
	public ImageViewPagerAdapter(FragmentManager fm, String[] imageurl) {
		super(fm);
		mImageUrl = imageurl;
	}
	@Override
	public float getPageWidth(int position) {
		// TODO Auto-generated method stub
		return 1f;
	}
	
	@Override
	public int getCount() {
		return mImageUrl.length;
	}
	@Override
	public Fragment getItem(int position) {
		return ImageViewFragment.newInstance(mImageUrl[position], position);
	}
}
