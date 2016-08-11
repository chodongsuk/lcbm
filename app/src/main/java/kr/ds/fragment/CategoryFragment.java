package kr.ds.fragment;

import java.util.ArrayList;

import in.srain.cube.views.GridViewWithHeaderAndFooter;
import kr.com.lcbm.CaseActivity;
import kr.com.lcbm.CustomWebViewActivity;
import kr.com.lcbm.ListActivity;
import kr.com.lcbm.R;
import kr.com.lcbm.RegisActivity;
import kr.com.lcbm.SearchActivity;
import kr.com.lcbm.ViewActivity;
import kr.ds.adapter.CategoryAdapter;
import kr.ds.adapter.ListAdapter;
import kr.ds.data.BaseResultListener;
import kr.ds.data.CategoryData;
import kr.ds.data.ListData;
import kr.ds.handler.CategoryHandler;
import kr.ds.handler.ListHandler;
import kr.ds.utils.DsObjectUtils;
import kr.ds.widget.CustomViewPager;
import kr.ds.widget.ScrollGridView;
import kr.ds.widget.ViewpagerNavibar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterViewFlipper;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import static com.google.android.gms.internal.zzir.runOnUiThread;


public class CategoryFragment extends BaseFragment implements View.OnClickListener{
	private ArrayList<CategoryHandler> mData;
	private Context mContext;
	private View mView;
	private CategoryData mCategoryData;
	private ScrollGridView mGridView;
	private CategoryAdapter mCategoryAdapter;
	private ProgressBar mProgressBar;
	private ScrollView mScrollViewContainer;
	private AdapterViewFlipper mAdapterViewFlipper;
	private ListData mListData;
	private ListAdapter mListAdapter;
	private ArrayList<ListHandler> mData2;
	private EditText mEditTextMessage;
	private ImageButton mImageButtonSearch;
	private LinearLayout mLinearLayoutNavibar;
	private ViewpagerNavibar mViewpagerNavibar;
	private CustomViewPager mCustomViewPager;

	public static CategoryFragment newInstance() {
		CategoryFragment fragment = new CategoryFragment();
		Bundle args = new Bundle();
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
		mView = inflater.inflate(R.layout.category_fragment, null);
		mScrollViewContainer = (ScrollView)mView.findViewById(R.id.scrollView_container);
		mAdapterViewFlipper  = (AdapterViewFlipper)mView.findViewById(R.id.adapterViewFlipper);
		mEditTextMessage = (EditText)mView.findViewById(R.id.editText_message);

		mEditTextMessage.setImeOptions(EditorInfo.IME_ACTION_GO);
		mEditTextMessage
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
												  KeyEvent event) {
						// 요기서 입력된 이벤트가 무엇인지 찾아서 실행해 줌
						switch (actionId) {
							case EditorInfo.IME_ACTION_GO:
								InputMethodManager imm = (InputMethodManager) mContext
										.getSystemService(Context.INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(
										mEditTextMessage.getWindowToken(), 0);
				if(!DsObjectUtils.getInstance(mContext).isEmpty(mEditTextMessage.getText().toString())){
                    Intent NextIntent = new Intent(mContext, SearchActivity.class);
                    NextIntent.putExtra("search", mEditTextMessage.getText().toString());
                    startActivity(NextIntent);

                }else{
                    Toast.makeText(mContext, R.string.search_not, Toast.LENGTH_SHORT).show();
                }
								break;
						}
						return false;
					}
				});

		(mImageButtonSearch = (ImageButton)mView.findViewById(R.id.imageButton_search)).setOnClickListener(this);
		mProgressBar = (ProgressBar)mView.findViewById(R.id.progressBar);
		mGridView = (ScrollGridView)mView.findViewById(R.id.gridView);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				if(mData.get(position).getType().matches("V")){

					Intent NextIntent = new Intent(mContext, ListActivity.class);
					NextIntent.putExtra("data", mData.get(position));
					startActivity(NextIntent);

				}else if(mData.get(position).getType().matches("H")){

					Intent NextIntent = new Intent(mContext, CustomWebViewActivity.class);
					NextIntent.putExtra("data", mData.get(position));
					startActivity(NextIntent);

				}else if(mData.get(position).getType().matches("R")){

					Intent NextIntent = new Intent(mContext, RegisActivity.class);
					NextIntent.putExtra("data", mData.get(position));
					startActivity(NextIntent);


				}else if(mData.get(position).getType().matches("V2")){

					Intent NextIntent = new Intent(mContext, CaseActivity.class);
					NextIntent.putExtra("data", mData.get(position));
					startActivity(NextIntent);


				}
				
			}
		});
		mLinearLayoutNavibar = (LinearLayout) mView.findViewById(R.id.linearLayout_navibar);
		mCustomViewPager = (CustomViewPager)mView.findViewById(R.id.viewpager);

		mCustomViewPager.setCallback(new CustomViewPager.ResultListener() {
			@Override
			public <T> void OnComplete(T data, int nums) {
				ViewPager mViewPager = (ViewPager) data;
				mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
				mViewpagerNavibar = new ViewpagerNavibar(mContext, nums);
				mLinearLayoutNavibar.addView(mViewpagerNavibar);
			}
		});
		mCustomViewPager.init();
		return mView;
	}
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);


		mCategoryData = new CategoryData(mContext);
		mProgressBar.setVisibility(View.VISIBLE);
		mCategoryData.clear().setCallBack(
				new BaseResultListener() {

					@Override
					public <T> void OnComplete() {

					}

					@Override
					public <T> void OnComplete(final ArrayList<T> arrayList) {
						if(arrayList != null){

							mData = (ArrayList<CategoryHandler>) arrayList;
							mCategoryAdapter = new CategoryAdapter(mContext, mData);
							mGridView.setAdapter(mCategoryAdapter);



						}
						mListData = new ListData(mContext);
						mListData.clear().setCallBack(
								new BaseResultListener() {

									@Override
									public <T> void OnComplete() {

									}

									@Override
									public <T> void OnComplete(ArrayList<T> arrayList) {
										mProgressBar.setVisibility(View.GONE);
										if(arrayList != null){
											mData2 = (ArrayList<ListHandler>) arrayList;
											mListAdapter = new ListAdapter(mContext, mData2);
											mAdapterViewFlipper.setAdapter(mListAdapter);
											mAdapterViewFlipper.startFlipping();
										}

									}

									@Override
									public void OnError(String str) {
										mProgressBar.setVisibility(View.GONE);

									}
								}).setParam("?banner=1").getView();





					}

					@Override
					public void OnError(String str) {
						mListData = new ListData(mContext);
						mListData.clear().setCallBack(
								new BaseResultListener() {

									@Override
									public <T> void OnComplete() {

									}

									@Override
									public <T> void OnComplete(ArrayList<T> arrayList) {
										mProgressBar.setVisibility(View.GONE);
										if(arrayList != null){
											mData2 = (ArrayList<ListHandler>) arrayList;
											mListAdapter = new ListAdapter(mContext, mData2);
											mAdapterViewFlipper.setAdapter(mListAdapter);
											mAdapterViewFlipper.startFlipping();
										}

									}

									@Override
									public void OnError(String str) {
										mProgressBar.setVisibility(View.GONE);

									}
								}).setParam("?banner=1").getView();
					}
				}).getView();


	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mAdapterViewFlipper.isFlipping()) {
			mAdapterViewFlipper.stopFlipping();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if(!mAdapterViewFlipper.isFlipping()){
			mAdapterViewFlipper.startFlipping();
		}
	}

	@Override
	public void onClick(View v) {

		if(!mEditTextMessage.getText().toString().equals("")){
			InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mEditTextMessage.getWindowToken(), 0);

			Intent NextIntent = new Intent(mContext, SearchActivity.class);
			NextIntent.putExtra("search", mEditTextMessage.getText().toString());
			startActivity(NextIntent);
		}else{
				Toast.makeText(mContext, R.string.search_not, Toast.LENGTH_SHORT).show();
		}

	}
}
