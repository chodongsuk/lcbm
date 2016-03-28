package kr.ds.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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


    public CustomViewPager(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public CustomViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
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
                    mViewPager.setPadding(mScreenUtils.getPixelFromDPI(mContext, 10),0,mScreenUtils.getPixelFromDPI(mContext, 10),0);
                    mViewPager.setClipToPadding(false);
                    mViewPager.setPageMargin(mScreenUtils.getPixelFromDPI(mContext, 4));
                    mCustomViewPagerAdapter = new CustomViewPagerAdapter(mContext, mData);
                    mViewPager.setAdapter(mCustomViewPagerAdapter);
                    addView(mViewPager);
                }

            }

            @Override
            public void OnError(String str) {

            }
        }).getView();

        return this;

    }




}
