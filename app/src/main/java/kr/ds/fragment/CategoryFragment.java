package kr.ds.fragment;

import java.util.ArrayList;

import kr.com.lcbm.ListActivity;
import kr.com.lcbm.R;
import kr.ds.adapter.CategoryAdapter;
import kr.ds.data.BaseResultListener;
import kr.ds.data.CategoryData;
import kr.ds.handler.CategoryHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;


public class CategoryFragment extends BaseFragment {
	private ArrayList<CategoryHandler> mData;
	private Context mContext;
	private View mView;
	private CategoryData mCategoryData;
	private GridView mGridView;
	private CategoryAdapter mCategoryAdapter;
	private ProgressBar mProgressBar;
	
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
		mProgressBar = (ProgressBar)mView.findViewById(R.id.progressBar);
		mGridView = (GridView)mView.findViewById(R.id.gridView);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				Intent NextIntent = new Intent(mContext, ListActivity.class);
				NextIntent.putExtra("data", mData.get(position));
				startActivity(NextIntent);
				
			}
		});
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
					public <T> void OnComplete(ArrayList<T> arrayList) {
						mProgressBar.setVisibility(View.GONE);
						if(arrayList != null){
							mData = (ArrayList<CategoryHandler>) arrayList;
							mCategoryAdapter = new CategoryAdapter(mContext, mData);
							mGridView.setAdapter(mCategoryAdapter);
						}

					}

					@Override
					public void OnError(String str) {
						mProgressBar.setVisibility(View.GONE);
					}
				}).getView();


	}

}
