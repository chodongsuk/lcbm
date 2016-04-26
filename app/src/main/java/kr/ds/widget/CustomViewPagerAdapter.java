package kr.ds.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import java.util.ArrayList;

import kr.com.lcbm.R;
import kr.com.lcbm.WebActivity;
import kr.ds.handler.EventHandler;

/**
 * Created by Administrator on 2016-03-28.
 */
public class CustomViewPagerAdapter extends PagerAdapter {
    private ArrayList<EventHandler> mArrayList;
    private Context mContext;
    private final ImageLoader imageDownloader = ImageLoader.getInstance();

    public CustomViewPagerAdapter(Context context, ArrayList<EventHandler> data){
        mContext = context;
        mArrayList = data;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_viewpager_item, null);
        ImageView ivImage = (ImageView) view.findViewById(R.id.imageView);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NextIntent = new Intent(mContext, WebActivity.class);
                NextIntent.putExtra("data", mArrayList.get(position));
                mContext.startActivity(NextIntent);
            }
        });

        imageDownloader.displayImage(mArrayList.get(position).getEd_image(), ivImage,
                new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onLoadingFailed(String arg0, View arg1,
                                                FailReason arg2) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onLoadingComplete(String arg0, View arg1,
                                                  Bitmap arg2) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                        // TODO Auto-generated method stub

                    }
                });
        container.addView(view);
        return view;

    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout rl = (LinearLayout) object;
        if (rl != null) {
            ImageView ivImage = (ImageView) rl.findViewById(R.id.imageView);
            imageDownloader.cancelDisplayTask(ivImage);
        }
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }




}