package kr.com.lcbm;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;

import kr.ds.fragment.CategoryFragment;
import kr.ds.fragment.ListFragment;
import kr.ds.handler.CategoryHandler;
import kr.ds.permission.Permission;
import kr.ds.utils.DsObjectUtils;

/**
 * Created by Administrator on 2016-03-21.
 */
public class ListActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mListView;
    private Toolbar mToolbar;

    private CategoryHandler mSavedata;

    private FragmentManager mFm;
    private FragmentTransaction mFt;
    private Fragment mFragment = null;

    private Permission cPermission;
    private boolean isPermission = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedata = (CategoryHandler) getIntent().getParcelableExtra("data");
        setContentView(R.layout.activity_list);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(mSavedata.getName());
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        cPermission = new Permission(ListActivity.this);
        cPermission.setCallback(new Permission.PermissionListener() {
            @Override
            public void onSuccess() {
                mFragment = ListFragment.newInstance(mSavedata.getCode(), true);
                mFm = getSupportFragmentManager();
                mFt = mFm.beginTransaction();
                mFt.replace(R.id.content_frame, mFragment);
                mFt.commit();

            }

            @Override
            public void onCancle() {
                Toast.makeText(getApplicationContext(), "어플 권한 전화 설정 해주시면 사용 가능합니다.", Toast.LENGTH_SHORT).show();
                mFragment = ListFragment.newInstance(mSavedata.getCode(), isPermission);
                mFm = getSupportFragmentManager();
                mFt = mFm.beginTransaction();
                mFt.replace(R.id.content_frame, mFragment);
                mFt.commit();

            }

            @Override
            public void requestPermissions(String[] type) {
                ActivityCompat.requestPermissions(ListActivity.this, type, 0);
            }
        }).isCall();



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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        isPermission = true;
        if(cPermission != null) {
            cPermission.setRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(isPermission && cPermission != null){
            cPermission.onRequestPermissionsResult(cPermission.getRequestCode(), cPermission.getPermissions(), cPermission.getGrantResults());
            isPermission = false;
        }
    }
}
