package kr.ds.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import kr.ds.utils.DsObjectUtils;

public class ImageViewFragment extends BaseFragment{
	private Context mContext;
	private View mView;
	private String mUrl;
	private LinearLayout mLinearLayout;
	private ImageView mImageView;
	private final ImageLoader imageDownloader = ImageLoader.getInstance();
	private int mPostion = 0;
	
	public static ImageViewFragment newInstance(String url, int position) {
		ImageViewFragment f = new ImageViewFragment();
		Bundle args = new Bundle();
		args.putString("url", url);
		f.setArguments(args);
		return f;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mContext = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mUrl = getArguments().getString("url");
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		mLinearLayout.setGravity(Gravity.CENTER);
		mImageView = new ImageView(mContext);
		mImageView.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
		mImageView.setScaleType(ScaleType.CENTER_CROP);
		mLinearLayout.addView(mImageView);
		mView = mLinearLayout;
		return mView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		if(!DsObjectUtils.getInstance(mContext).isEmpty(mUrl)){
			imageDownloader.displayImage(mUrl,mImageView, new ImageLoadingListener() {
				
				@Override
				public void onLoadingStarted(String arg0, View arg1) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onLoadingCancelled(String arg0, View arg1) {
					// TODO Auto-generated method stub
				}
			});
		}
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

}
