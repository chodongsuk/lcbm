package kr.ds.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import kr.com.lcbm.PopupImageViewActivity;
import kr.com.lcbm.R;
import kr.com.lcbm.ViewActivity;
import kr.ds.adapter.CaseAdapter;
import kr.ds.adapter.ImageViewPagerAdapter;
import kr.ds.adapter.ListAdapter;
import kr.ds.data.BaseResultListener;
import kr.ds.data.CaseBannerData;
import kr.ds.data.ListData;
import kr.ds.handler.CaseBannerHandler;
import kr.ds.handler.ListHandler;
import kr.ds.utils.ScreenUtils;
import kr.ds.widget.ScrollGridView;
import kr.ds.widget.ViewpagerNavibar;


public class CaseFragment extends BaseFragment {
	private ArrayList<ListHandler> mData;
	private ArrayList<CaseBannerHandler> mCaseBannerData;
	private Context mContext;
	private View mView;
	private ListData mListData;
	private ScrollGridView mGridView;
	private CaseAdapter mCaseAdapter;
	private ProgressBar mProgressBar;
	private String mCode;

	private ViewPager mViewPagerHeader;
	private ImageViewPagerAdapter mImageViewPagerAdapter;
	private LinearLayout mLinearLayoutNavibar;
	private ViewpagerNavibar mViewpagerNavibar;
	private int mDeviceHeight;
	private int mDeviceWidth;
	private float mViewPagerHeight;
	private final float IMAGE_WIDTH = 1f;
	private final float IMAGE_WH_RATIO = 0.4f;
	private Point pt;

	public static CaseFragment newInstance(String code) {
		CaseFragment fragment = new CaseFragment();
		Bundle args = new Bundle();
		args.putString("code", code);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mContext = getActivity();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		mCode = getArguments().getString("code");

		mView = inflater.inflate(R.layout.case_fragment, null);
		mLinearLayoutNavibar = (LinearLayout) mView.findViewById(R.id.linearLayout_navibar);
		mViewPagerHeader = (ViewPager) mView.findViewById(R.id.viewPager_imageview);
		mProgressBar = (ProgressBar)mView.findViewById(R.id.progressBar);
		mGridView = (ScrollGridView) mView.findViewById(R.id.gridView);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent NextIntent = new Intent(mContext, PopupImageViewActivity.class);

				NextIntent.putExtra("urls", mData.get(position).getSub_images());
				NextIntent.putExtra("position", 0);
				mContext.startActivity(NextIntent);
			}
		});
		return mView;
	}
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mListData = new ListData(mContext);
		mProgressBar.setVisibility(View.VISIBLE);

		new CaseBannerData(mContext).clear().setCallBack(new BaseResultListener() {
			@Override
			public <T> void OnComplete() {

			}

			@Override
			public <T> void OnComplete(ArrayList<T> arrayList) {
				if(arrayList != null) {
					mCaseBannerData = (ArrayList<CaseBannerHandler>) arrayList;
					String[] images = new String[mCaseBannerData.size()];
					for(int i=0; i<mCaseBannerData.size(); i++){
						images[i] = mCaseBannerData.get(i).getBd_image();
					}
					mImageViewPagerAdapter = new ImageViewPagerAdapter(getChildFragmentManager(), images);

					mViewPagerHeader.setAdapter(mImageViewPagerAdapter);
					DisplayMetrics displaymetrics = new DisplayMetrics();
					getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
					mDeviceWidth = displaymetrics.widthPixels;
					mDeviceHeight = displaymetrics.heightPixels;
					mViewPagerHeight = ((mDeviceWidth * IMAGE_WIDTH) * IMAGE_WH_RATIO); //(가로 * 이미지 보이는영역) * 이미지 세로 비율
					mViewPagerHeader.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) mViewPagerHeight));
					mViewPagerHeader.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
					mViewpagerNavibar = new ViewpagerNavibar(mContext, images.length);
					mLinearLayoutNavibar.addView(mViewpagerNavibar);


				}
			}

			@Override
			public void OnError(String str) {

			}
		}).getView();




		mListData.clear().setCallBack(
							new BaseResultListener() {

						@Override
						public <T> void OnComplete() {

						}

						@Override
						public <T> void OnComplete(ArrayList<T> arrayList) {
							mProgressBar.setVisibility(View.GONE);
							if(arrayList != null){
								mData = (ArrayList<ListHandler>) arrayList;
								mCaseAdapter = new CaseAdapter(mContext, mData);
								mGridView.setAdapter(mCaseAdapter);
							}

						}

						@Override
						public void OnError(String str) {
							mProgressBar.setVisibility(View.GONE);

						}
				}).setParam("?code="+mCode).getView();


	}

}
