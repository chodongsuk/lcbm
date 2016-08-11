package kr.ds.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import kr.ds.fragment.PopupImageViewFragment;

public class PopupImageViewPagerAdapter extends FragmentStatePagerAdapter {
	private String[] mImageUrl;
	public PopupImageViewPagerAdapter(FragmentManager fm, String[] imageurl) {
		super(fm);
		mImageUrl = imageurl;
	}
	@Override
	public int getCount() {
		return mImageUrl.length;
	}
	@Override
	public Fragment getItem(int position) {
		return PopupImageViewFragment.newInstance(mImageUrl[position]);
	}
}
