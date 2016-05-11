package kr.ds.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import kr.com.lcbm.R;
import kr.ds.utils.ScreenUtils;

@SuppressLint("NewApi")
public class ViewpagerNavibar extends LinearLayout{
	private Context mContext;
	private int mTotal = 0;
	private ImageView[] mImageView;
	private LinearLayout mLinearLayout;
	private final int VISIBLE = R.drawable.img_on;
	private final int GONE = R.drawable.img_off;;
	
	public ViewpagerNavibar(Context context) {
		super(context);
		mContext = context;
	}

	public ViewpagerNavibar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public ViewpagerNavibar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
	}

	public ViewpagerNavibar(Context context, int total) {
		super(context);
		mContext = context;
		mTotal = total;
		init();
		// TODO Auto-generated constructor stub
	}

	public void setTotal(int total){
		mTotal = total;
		init();
	}

	public void init(){
		removeAllViews();
		mImageView = new ImageView[mTotal];
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setOrientation(HORIZONTAL);
		for(int i=0; i<mTotal; i++){
			mImageView[i] = new ImageView(mContext);
			mImageView[i].setLayoutParams(getLayoutParams());
			if(i == 0){
				mImageView[i].setBackgroundResource(VISIBLE);
			}else{
				mImageView[i].setBackgroundResource(GONE);
			}
			mLinearLayout.addView(mImageView[i]);
		}
		addView(mLinearLayout);
	}
	public LayoutParams getLayoutParams(){
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.setMargins(ScreenUtils.getInstacne().getPixelFromDPI(mContext, 2), 0, ScreenUtils.getInstacne().getPixelFromDPI(mContext, 2), 0);
		return lp;
	}
	public void setButton(int position){
		for(int i=0; i<mTotal; i++){
			if(position == i){
				mImageView[i].setBackgroundResource(VISIBLE);
			}else{
				mImageView[i].setBackgroundResource(GONE);
			}
		}
	}
	
	
	
	
}
