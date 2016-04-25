package kr.ds.adapter;

import java.util.ArrayList;

import kr.ds.handler.CategoryHandler;
import kr.ds.handler.MenuHandler;

import android.content.Context;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<CategoryHandler> mData;
	private View mView;
	//LayoutInflater mInflater;
	
	public MenuAdapter(Context context, ArrayList<CategoryHandler> data){
		mContext = context;
		mData = data;
		//mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		mView = setLayout(mData.get(position).getName(), position, mData.get(position).getIsBg());
		return mView;
	}
	
	public LinearLayout setLayout(String menutitle, int position, boolean bg){
		LinearLayout mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setPadding(0, dipToInt(20), 0, dipToInt(20));
		mLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
		mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		ImageView mImageView = new ImageView(mContext);
//		if(position == 0){
//			mImageView.setImageResource(R.drawable.side_icon1);
//		}else if(position == 1){
//			mImageView.setImageResource(R.drawable.side_icon2);
//		}else if(position == 2){
//			mImageView.setImageResource(R.drawable.side_icon3);
//		}else if(position == 3){
//			mImageView.setImageResource(R.drawable.side_icon4);
//		}else if(position == 4){
//			mImageView.setImageResource(R.drawable.side_icon5);
//		}else if(position == 5){
//			mImageView.setImageResource(R.drawable.side_icon6);
//		}else if(position == 6){
//			mImageView.setImageResource(R.drawable.side_icon7);
//		}
		mImageView.setPadding(dipToInt(10), 0, 0, 0);
		
		TextView mTextView = new TextView(mContext);
		mTextView.setPadding(dipToInt(10), 0, 0, 0);
		mTextView.setText(menutitle);
		mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
		mTextView.setPaintFlags(mTextView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		if(bg){
			mTextView.setTextColor(0xff3fb8ec);
		}else{
			mTextView.setTextColor(0xff555555);
		}
		mLinearLayout.addView(mImageView);
		mLinearLayout.addView(mTextView);
		return mLinearLayout;
	}
	private int dipToInt(int number) {
		int num = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				number, mContext.getResources().getDisplayMetrics());
		return num;
	}
}