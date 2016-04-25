package kr.com.lcbm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.ContentHandler;

import kr.ds.fragment.WorkaroundMapFragment;
import kr.ds.handler.CategoryHandler;
import kr.ds.handler.ListHandler;
import kr.ds.utils.DsObjectUtils;
import kr.ds.widget.ContentViewPager;

/**
 * Created by Administrator on 2016-03-21.
 */
public class ViewActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mListView;
    private Toolbar mToolbar;
    private ScrollView mScrollView;
    private ListHandler mSavedata;
    private TextView mTextViewContet;

    private Button mButtonTell, mButtonShare;
    private ContentViewPager mContetContentViewPager;

    private GoogleMap mGoogleMap;
    private WorkaroundMapFragment mMapFragment;
    private double mLat = 37.554531;
    private double mLon = 126.970663;
    private int mGoogleZoom = 17;

    private TextView mTextViewTema, mTextViewAddress, mTextViewTell, mTextViewTime, mTextViewHoliday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedata = (ListHandler) getIntent().getParcelableExtra("data");
        setContentView(R.layout.activity_view);
        mTextViewContet = (TextView)findViewById(R.id.textView_content);
        (mButtonTell = (Button)findViewById(R.id.button_tell)).setOnClickListener(this);
        (mButtonShare = (Button)findViewById(R.id.button_share)).setOnClickListener(this);
        mContetContentViewPager = (ContentViewPager)findViewById(R.id.viewpager);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);

        mTextViewTema = (TextView)findViewById(R.id.textView_tema);
        mTextViewAddress = (TextView)findViewById(R.id.textView_address);
        mTextViewTell = (TextView)findViewById(R.id.textView_tell);
        mTextViewTime = (TextView)findViewById(R.id.textView_time);
        mTextViewHoliday = (TextView)findViewById(R.id.textView_holiday);


        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) == ConnectionResult.SUCCESS) {
            GoogleMapOptions options = new GoogleMapOptions();
            mMapFragment = (WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mMapFragment.getMapAsync(this);
            mMapFragment.setListener(new WorkaroundMapFragment.OnTouchListener() {
                @Override
                public void onTouch() {
                    // TODO Auto-generated method stub
                    mScrollView.requestDisallowInterceptTouchEvent(true);
                }

            });
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mSavedata.getGd_name())){
                mToolbar.setTitle(mSavedata.getGd_name());
            }else{
                mToolbar.setTitle(getResources().getString(R.string.app_name));
            }

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mSavedata.getGd_content())){
            mTextViewContet.setText(Html.fromHtml(mSavedata.getGd_content()));
        }

        if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mSavedata.getSub_images())){
            mContetContentViewPager.setVisibility(View.VISIBLE);
                String[] mDatas = new String[(mSavedata.getSub_images().split(",").length)];
            for(int i=0; i<mSavedata.getSub_images().split(",").length; i++){
                mDatas[i] = mSavedata.getSub_images().split(",")[i];
            }
            mContetContentViewPager.setView(mDatas);
        }else{
            mContetContentViewPager.setVisibility(View.GONE);
        }

        if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mSavedata.getGd_lat())){
            mLat = Double.parseDouble(mSavedata.getGd_lat());
        }
        if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mSavedata.getGd_lon())){
            mLon = Double.parseDouble(mSavedata.getGd_lon());
        }

        if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mSavedata.getGd_tema())){
            mTextViewTema.setVisibility(View.VISIBLE);
            mTextViewTema.setText("업무 : "+mSavedata.getGd_tema());
        }else{
            mTextViewTema.setVisibility(View.GONE);
        }

        if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mSavedata.getGd_address())){
            mTextViewAddress.setVisibility(View.VISIBLE);
            mTextViewAddress.setText("주소 : "+mSavedata.getGd_address());
        }else{
            mTextViewAddress.setVisibility(View.GONE);
        }


        if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mSavedata.getGd_tell())){
            mTextViewTell.setVisibility(View.VISIBLE);
            mTextViewTell.setText("전화번호 : "+mSavedata.getGd_tell());
        }else{
            mTextViewTell.setVisibility(View.GONE);
        }

        if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mSavedata.getGd_time())){
            mTextViewTime.setVisibility(View.VISIBLE);
            mTextViewTime.setText("영업시간 : "+mSavedata.getGd_time());
        }else{
            mTextViewTime.setVisibility(View.GONE);
        }

        if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mSavedata.getGd_holiday())){
            mTextViewHoliday.setVisibility(View.VISIBLE);
            mTextViewHoliday.setText("휴무일 : "+mSavedata.getGd_holiday());
        }else{
            mTextViewHoliday.setVisibility(View.GONE);
            mTextViewHoliday.setText("");
        }
    }
    @Override
    public void onMapReady(GoogleMap arg0) {
        // TODO Auto-generated method stub
        mGoogleMap = arg0;
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLat, mLon), mGoogleZoom));
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(mLat, mLon)).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_icon)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void SetTell(final String tell){
        if (!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(tell)) {
            AlertDialog.Builder alt_bld = new AlertDialog.Builder(ViewActivity.this);
            alt_bld.setMessage("전화 연결 하시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("전화걸기",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try{
                                        Intent NextIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tell));
                                        NextIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(NextIntent);
                                    }catch (Exception e) {
                                        // TODO: handle exception
                                        Toast.makeText(getApplicationContext(), "계속 문제가 발생시 관리자에게 문의해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
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

    private void SendMMS() {
        if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mSavedata.getGd_content()) && !DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mSavedata.getGd_name())) {
            try {
                Intent NextIntent = new Intent(Intent.ACTION_SEND);
                NextIntent.setType("text/plain");
                NextIntent.putExtra(Intent.EXTRA_SUBJECT, mSavedata.getGd_name());
                NextIntent.putExtra(Intent.EXTRA_TEXT, mSavedata.getGd_tema()+"\n"+mSavedata.getGd_address()+"\n"+mSavedata.getGd_time()+"\n"+mSavedata.getGd_tell()+"\n"+mSavedata.getGd_holiday());
                startActivity(Intent.createChooser(NextIntent, mSavedata.getGd_name() + "공유하기"));
            } catch (Exception e) {
                // TODO: handle exception
                Log.i("TEST", e.toString() + "");
            }
        }else {
            Toast.makeText(getApplicationContext(),"계속 문제가 발생시 관리자에게 문의해주시기 바랍니다.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_tell:
                if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(mSavedata.getGd_tell())) {
                    SetTell(mSavedata.getGd_tell());
                }
                break;
            case R.id.button_share:
                SendMMS();
                break;
        }
    }
}
