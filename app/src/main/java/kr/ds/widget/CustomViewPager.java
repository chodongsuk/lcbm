package kr.ds.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;

import kr.ds.data.BaseResultListener;
import kr.ds.data.EventData;
import kr.ds.handler.EventHandler;
import kr.ds.utils.ScreenUtils;

/**
 * Created by Administrator on 2016-03-28.
 */
@SuppressLint("NewApi")
public class CustomViewPager extends LinearLayout{
    private ViewPager mViewPager;
    private CustomViewPagerAdapter mCustomViewPagerAdapter;
    private EventData mEventData;
    private ArrayList<EventHandler> mData;
    private Context mContext;
    private ScreenUtils mScreenUtils = ScreenUtils.getInstacne();

    private ResultListener mResultListener;
    public CustomViewPager setCallback(ResultListener listener) {
        mResultListener = listener;
        return this;
    }
    public interface ResultListener {
        public <T> void OnComplete(T data, int nums);
    }

    public CustomViewPager(Context context) {
        super(context);
        mContext = context;
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public CustomViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }



    public CustomViewPager init(){
        mEventData = new EventData(mContext);
        mEventData.clear().setCallBack(new BaseResultListener() {
            @Override
            public <T> void OnComplete() {

            }

            @Override
            public <T> void OnComplete(ArrayList<T> arrayList) {
                if (arrayList != null) {
                    mData = (ArrayList<EventHandler>) arrayList;

                    mViewPager = new ViewPager(mContext);
                    mViewPager.setPadding(mScreenUtils.getPixelFromDPI(mContext, 7),0,mScreenUtils.getPixelFromDPI(mContext, 7),0);
                    //mViewPager.setClipToPadding(false);
                    //mViewPager.setPageMargin(mScreenUtils.getPixelFromDPI(mContext, 4));
                    mCustomViewPagerAdapter = new CustomViewPagerAdapter(mContext, mData);
                    mViewPager.setAdapter(mCustomViewPagerAdapter);
                    addView(mViewPager);
                    if(mResultListener != null){
                        mResultListener.OnComplete(mViewPager, (mData.size() % 3 == 0) ? (mData.size() / 3) : (mData.size()/3)+1);
                    }
                }

            }

            @Override
            public void OnError(String str) {

            }
        }).getView();

        return this;

    }





}
