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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ContentHandler;

import kr.ds.handler.CategoryHandler;
import kr.ds.handler.ListHandler;
import kr.ds.utils.DsObjectUtils;

/**
 * Created by Administrator on 2016-03-21.
 */
public class ViewActivity extends BaseActivity implements View.OnClickListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mListView;
    private Toolbar mToolbar;

    private ListHandler mSavedata;
    private TextView mTextViewContet;

    private Button mButtonTell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedata = (ListHandler) getIntent().getParcelableExtra("data");
        setContentView(R.layout.activity_view);
        mTextViewContet = (TextView)findViewById(R.id.textView_content);
        (mButtonTell = (Button)findViewById(R.id.button_tell)).setOnClickListener(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            if(!DsObjectUtils.isEmpty(mSavedata.getGd_name())){
                mToolbar.setTitle(mSavedata.getGd_name());
            }else{
                mToolbar.setTitle(getResources().getString(R.string.app_name));
            }

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(!DsObjectUtils.isEmpty(mSavedata.getGd_content())){
            mTextViewContet.setText(Html.fromHtml(mSavedata.getGd_content()));
        }
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
        if (!DsObjectUtils.isEmpty(tell)) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_tell:
                if(!DsObjectUtils.isEmpty(mSavedata.getGd_tell())) {
                    SetTell(mSavedata.getGd_tell());
                }
                break;
        }
    }
}
