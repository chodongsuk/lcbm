package kr.ds.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import kr.ds.utils.ScreenUtils;

/**
 * Created by Administrator on 2016-03-28.
 */
@SuppressLint("NewApi")
public class ContentViewPager extends LinearLayout{
    private ViewPager mViewPager;
    private ContentViewPagerAdapter mContentViewPagerAdapter;
    private Context mContext;
    private ScreenUtils mScreenUtils = ScreenUtils.getInstacne();
    private String[] mData;

    private ResultListener mResultListener;
    public ContentViewPager setCallback(ResultListener listener) {
        mResultListener = listener;
        return this;
    }
    public interface ResultListener {
        public <T> void OnComplete(T data, int nums);
    }

    public ContentViewPager(Context context) {
        super(context);
        mContext = context;
    }

    public ContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ContentViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public void setView(String[] data){
        mData   = data;
        mViewPager = new ViewPager(mContext);
        //mViewPager.setPadding(mScreenUtils.getPixelFromDPI(mContext, 7),0,mScreenUtils.getPixelFromDPI(mContext, 7),0);
        //mViewPager.setClipToPadding(false);
        //mViewPager.setPageMargin(mScreenUtils.getPixelFromDPI(mContext, 4));
        mContentViewPagerAdapter = new ContentViewPagerAdapter(mContext, mData);
        mViewPager.setAdapter(mContentViewPagerAdapter);
        addView(mViewPager);
        if(mResultListener != null){
            mResultListener.OnComplete(mViewPager, mData.length);
        }
    }

}
