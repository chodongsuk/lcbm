package kr.ds.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private int mRow = 3;

    public CustomViewPagerAdapter(Context context, ArrayList<EventHandler> data){
        mContext = context;
        mArrayList = data;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_viewpager_item, null);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.imageView3);

        TextView textView1 = (TextView) view.findViewById(R.id.textView1);
        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
        TextView textView3 = (TextView) view.findViewById(R.id.textView3);

        int c = 0;
        for(int i=1; i<=mRow; i++){
            c = ((position*mRow)+i)-1;
            if(mArrayList.size() > c){
                if(i == 1) {
                    imageDownloader.displayImage(mArrayList.get(c).getEd_image(), imageView1);
                    imageView1.setId(c);
                    imageView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = (int)v.getId();
                            Intent NextIntent = new Intent(mContext, WebActivity.class);
                            NextIntent.putExtra("data", mArrayList.get(position));
                            mContext.startActivity(NextIntent);
                        }
                    });
                    textView1.setText(mArrayList.get(c).getEd_name());
                    textView1.setVisibility(View.VISIBLE);
                    textView1.setId(c);
                    textView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        int position = (int)v.getId();
                            Intent NextIntent = new Intent(mContext, WebActivity.class);
                            NextIntent.putExtra("data", mArrayList.get(position));
                            mContext.startActivity(NextIntent);
                        }
                    });
                }else if(i == 2){
                    imageDownloader.displayImage(mArrayList.get(c).getEd_image(), imageView2);
                    imageView2.setId(c);
                    imageView2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = (int)v.getId();
                            Intent NextIntent = new Intent(mContext, WebActivity.class);
                            NextIntent.putExtra("data", mArrayList.get(position));
                            mContext.startActivity(NextIntent);
                        }
                    });
                    textView2.setText(mArrayList.get(c).getEd_name());
                    textView2.setId(c);
                    textView2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = (int)v.getId();
                            Intent NextIntent = new Intent(mContext, WebActivity.class);
                            NextIntent.putExtra("data", mArrayList.get(position));
                            mContext.startActivity(NextIntent);
                        }
                    });
                    textView2.setVisibility(View.VISIBLE);
                }else if(i == 3){
                    imageDownloader.displayImage(mArrayList.get(c).getEd_image(), imageView3);
                    imageView3.setId(c);
                    imageView3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = (int)v.getId();
                            Intent NextIntent = new Intent(mContext, WebActivity.class);
                            NextIntent.putExtra("data", mArrayList.get(position));
                            mContext.startActivity(NextIntent);
                        }
                    });
                    textView3.setText(mArrayList.get(c).getEd_name());
                    textView3.setId(c);
                    textView3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = (int)v.getId();
                            Intent NextIntent = new Intent(mContext, WebActivity.class);
                            NextIntent.putExtra("data", mArrayList.get(position));
                            mContext.startActivity(NextIntent);
                        }
                    });
                    textView3.setVisibility(View.VISIBLE);
                }
            }

        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        LinearLayout rl = (LinearLayout) object;
//        if (rl != null) {
//            ImageView ivImage = (ImageView) rl.findViewById(R.id.imageView);
//            if(imageDownloader != null) {
//                imageDownloader.cancelDisplayTask(ivImage);
//            }
//        }
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        int count = (mArrayList.size() % mRow == 0) ? (mArrayList.size() / mRow) : (mArrayList.size()/mRow)+1;
        return count;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }




}