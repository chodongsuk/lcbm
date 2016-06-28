package kr.ds.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import kr.com.lcbm.R;
import kr.com.lcbm.ViewActivity;
import kr.ds.adapter.ListAdapter;
import kr.ds.data.BaseResultListener;
import kr.ds.data.ListData;
import kr.ds.handler.ListHandler;


public class SearchFragment extends BaseFragment {
	private ArrayList<ListHandler> mData;
	private Context mContext;
	private View mView;
	private ListData mListData;
	private ListView mListView;
	private ListAdapter mListAdapter;
	private ProgressBar mProgressBar;
	private String mSearch;
	private Boolean isCall = false;
	public static SearchFragment newInstance(String search, Boolean iscall) {
		SearchFragment fragment = new SearchFragment();
		Bundle args = new Bundle();
		args.putString("search", search);
		args.putBoolean("iscall", iscall);
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

		mSearch = getArguments().getString("search");
		isCall = getArguments().getBoolean("iscall");

		mView = inflater.inflate(R.layout.list_fragment, null);
		mProgressBar = (ProgressBar)mView.findViewById(R.id.progressBar);
		mListView = (ListView)mView.findViewById(R.id.listView);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.i("TEST","onItemClick");
				Intent NextIntent = new Intent(mContext, ViewActivity.class);
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
		mListData = new ListData(mContext);
		mProgressBar.setVisibility(View.VISIBLE);
		try {
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
                                mListAdapter = new ListAdapter(mContext, mData);
                                mListView.setAdapter(mListAdapter);
                            }

                        }

                        @Override
                        public void OnError(String str) {
                            mProgressBar.setVisibility(View.GONE);

                        }
                    }).setParam("?search="+ URLEncoder.encode(mSearch,"utf-8")).getView();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}


	}

}
