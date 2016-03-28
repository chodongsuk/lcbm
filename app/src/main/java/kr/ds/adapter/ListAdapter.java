package kr.ds.adapter;

import java.util.ArrayList;

import kr.com.lcbm.R;
import kr.ds.handler.ListHandler;
import kr.ds.permission.Permission;
import kr.ds.utils.DsObjectUtils;
import kr.ds.utils.ScreenUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

/**
 * Created by Administrator on 2016-03-21.
 */
public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ListHandler> mData;
    private LayoutInflater mInflater;
    private final ImageLoader imageDownloader = ImageLoader.getInstance();
    private Boolean isCall = false;

    public ListAdapter(Context context, ArrayList<ListHandler> data , Boolean iscall) {
        mContext = context;
        mData = data;
        isCall = iscall;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    public int getWidth(){
        Point p = new Point();
        p.x = mContext.getResources().getDisplayMetrics().widthPixels;
        return p.x;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_fragment_item,null);
            holder.imageView = (RoundedImageView)convertView.findViewById(R.id.imageView);
            holder.textViewName = (TextView) convertView.findViewById(R.id.textView_name);
            holder.textViewTema = (TextView) convertView.findViewById(R.id.textView_tema);
            holder.textViewAddress = (TextView) convertView.findViewById(R.id.textView_address);
            holder.button = (Button) convertView.findViewById(R.id.button);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(!DsObjectUtils.isEmpty(mData.get(position).getGd_image())){
            imageDownloader.displayImage(mData.get(position).getGd_image(), holder.imageView, new ImageLoadingListener() {

                @Override
                public void onLoadingStarted(String arg0, View arg1) {
                    // TODO Auto-generated method stub
                    holder.imageView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                    // TODO Auto-generated method stub
                    holder.imageView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                    // TODO Auto-generated method stub
                    //int width = getWidth() - ScreenUtils.getInstacne().getPixelFromDPI(mContext, 12);
                    //int height = (int) (arg2.getHeight() * (width / (float) arg2.getWidth()));
                    //holder.imageView.setLayoutParams(new FrameLayout.LayoutParams(width, height));
                    holder.imageView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingCancelled(String arg0, View arg1) {
                    // TODO Auto-generated method stub
                }
            });
        }else{
            holder.imageView.setVisibility(View.VISIBLE);
        }


        if(!DsObjectUtils.isEmpty(mData.get(position).getGd_name())){
            holder.textViewName.setText(mData.get(position).getGd_name());
        }else{
            holder.textViewName.setText("");
        }
        if(!DsObjectUtils.isEmpty(mData.get(position).getGd_tema())){
            holder.textViewTema.setText(mData.get(position).getGd_tema());
        }else{
            holder.textViewTema.setText("");
        }
        if(!DsObjectUtils.isEmpty(mData.get(position).getGd_address())){
            holder.textViewAddress.setText(mData.get(position).getGd_address());
        }else{
            holder.textViewAddress.setText("");
        }
        if(!DsObjectUtils.isEmpty(mData.get(position).getGd_tell()) && isCall){
            holder.button.setVisibility(View.VISIBLE);
        }else{
            holder.button.setVisibility(View.GONE);
        }
        holder.button.setId(position);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getId();
                SetTell(mData.get(position).getGd_tell());
            }
        });



        return convertView;
    }

    private void SetTell(final String tell){
        if (!DsObjectUtils.isEmpty(tell)) {
            AlertDialog.Builder alt_bld = new AlertDialog.Builder(mContext);
            alt_bld.setMessage("전화 연결 하시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("전화걸기",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try{
                                        Intent NextIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tell));
                                        NextIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mContext.startActivity(NextIntent);
                                    }catch (Exception e) {
                                        // TODO: handle exception
                                        Toast.makeText(mContext, "계속 문제가 발생시 관리자에게 문의해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                    .setNegativeButton("취소하기", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Action for 'NO' Button
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alt_bld.create();
            alert.show();
        }
    }
    class ViewHolder {
        RoundedImageView imageView;
        TextView textViewName;
        TextView textViewTema;
        TextView textViewAddress;
        Button button;

    }
}