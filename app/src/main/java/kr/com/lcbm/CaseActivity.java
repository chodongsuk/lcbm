package kr.com.lcbm;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import kr.ds.fragment.CaseFragment;
import kr.ds.fragment.ListFragment;
import kr.ds.handler.CategoryHandler;
import kr.ds.permission.Permission;

/**
 * Created by Administrator on 2016-08-11.
 */
public class CaseActivity  extends BaseActivity {

    private Toolbar mToolbar;
    private CategoryHandler mSavedata;

    private FragmentManager mFm;
    private FragmentTransaction mFt;
    private Fragment mFragment = null;

    private Permission cPermission;
    private boolean isPermission = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mSavedata = (CategoryHandler) getIntent().getParcelableExtra("data");
        setContentView(R.layout.activity_case);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(mSavedata.getName());
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mFragment = CaseFragment.newInstance(mSavedata.getCode());
        mFm = getSupportFragmentManager();
        mFt = mFm.beginTransaction();
        mFt.replace(R.id.content_frame, mFragment);
        mFt.commit();

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



}
