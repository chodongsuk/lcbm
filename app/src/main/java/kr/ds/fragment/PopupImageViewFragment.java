package kr.ds.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import kr.ds.utils.DsObjectUtils;
import kr.ds.widget.TouchImageView;

public class PopupImageViewFragment extends BaseFragment{
	private Context mContext;
	private View mView;
	private String mUrl;
	private TouchImageView mImageView;
	private LinearLayout mBg;
	
	private final ImageLoader imageDownloader = ImageLoader.getInstance();
	private Bitmap mBitmap = null;
	
	public static PopupImageViewFragment newInstance(String url) {
		PopupImageViewFragment f = new PopupImageViewFragment();
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
		mImageView = new TouchImageView(mContext);
		mBg = new LinearLayout(mContext);
		mBg.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		mImageView.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		mBg.setBackgroundColor(Color.BLACK);
		mBg.addView(mImageView);
		mView = mBg;
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

//					mBitmap = fillHeight(arg2, getHeight());
//					if(mBitmap != null){
//						mImageView.setImageBitmap(mBitmap);
//					}
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
		if(mBitmap != null){
			mBitmap.recycle();
		}
	}

}
