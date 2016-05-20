package kr.com.lcbm;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

import kr.ds.adapter.CategoryAdapter;
import kr.ds.adapter.MenuAdapter;
import kr.ds.data.BaseResultListener;
import kr.ds.data.CategoryData;
import kr.ds.fragment.BaseFragment;
import kr.ds.fragment.CategoryFragment;
import kr.ds.handler.CategoryHandler;
import kr.ds.handler.MenuHandler;
import kr.ds.permission.Permission;
import kr.ds.utils.DsObjectUtils;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mListView;
    private Toolbar mToolbar;

    private FragmentManager mFm;
    private FragmentTransaction mFt;
    private Fragment mFragment = null;

    private int mMenuPosition = -1; //디폴트
    private MenuAdapter mMenuAdapter;
    private Bundle mBundle = null;

    private MenuItem mSearchItem;

    private CategoryData mCategoryData;
    private ArrayList<CategoryHandler> mMenuData;

    private SharedPreferences sharedPreferences;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public static final int AREA_RESULTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            mBundle = savedInstanceState;
            mMenuPosition = savedInstanceState.getInt("menu_position");
        }
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (checkPlayServices() && sharedPreferences.getString(QuickstartPreferences.TOKEN,"").matches("")) { //토큰이 없는경우..
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                boolean sentToken = sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Log.i(TAG, "Registration Token sucessful " );
                } else {
                    Log.i(TAG, "Registration Token failed " );
                }
            }
        };


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mListView = (ListView) findViewById(R.id.navdrawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(R.drawable.side_menu);
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
        }
        mDrawerToggle = new ToolbarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mCategoryData = new CategoryData(getApplicationContext());
        mCategoryData.clear().setCallBack(
                new BaseResultListener() {

                    @Override
                    public <T> void OnComplete() {

                    }

                    @Override
                    public <T> void OnComplete(final ArrayList<T> arrayList) {

                        if(arrayList != null){
                            mMenuData = (ArrayList<CategoryHandler>) arrayList;
                            mMenuAdapter = new MenuAdapter(getApplicationContext(),mMenuData);
                            mListView.setOnItemClickListener(new DrawerItemClickListener());
                            mListView.setAdapter(mMenuAdapter);

                        }
                    }

                    @Override
                    public void OnError(String str) {
                    }
                }).getView();

        if(mMenuPosition != -1){//메뉴설정
            mMenuData.get(mMenuPosition).setIsBg(true);
            mMenuAdapter.notifyDataSetChanged();
        }
        if (savedInstanceState == null) {
            selectItem(-1);
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    private final class ToolbarDrawerToggle extends ActionBarDrawerToggle {

        public ToolbarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }
        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
        }
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
        }
    }
    private void selectItem(int position){
        if(position == -1){
            mFragment = CategoryFragment.newInstance();
            setFragment(mFragment);
        }else if(position >= 0){
            if(mMenuData.get(position).getType().matches("V")){

                Intent NextIntent = new Intent(getApplicationContext(), ListActivity.class);
                NextIntent.putExtra("data", mMenuData.get(position));
                startActivity(NextIntent);

            }else if(mMenuData.get(position).getType().matches("H")){

                Intent NextIntent = new Intent(getApplicationContext(), CustomWebViewActivity.class);
                NextIntent.putExtra("data", mMenuData.get(position));
                startActivity(NextIntent);

            }else if(mMenuData.get(position).getType().matches("R")){

                Intent NextIntent = new Intent(getApplicationContext(), RegisActivity.class);
                NextIntent.putExtra("data", mMenuData.get(position));
                startActivity(NextIntent);

            }
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("menu_position", mMenuPosition);
    }
    private void setFragment(Fragment fragment){

        mFm = getSupportFragmentManager();
        mFt = mFm.beginTransaction();
        mFt.replace(R.id.content_frame, fragment);
        mFt.commit();

        mDrawerLayout.closeDrawer(GravityCompat.START);

    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id){
            mMenuPosition = position;
            for(int i=0; i< mMenuData.size(); i++){
                mMenuData.get(i).setIsBg(false);
            }
            mMenuData.get(position).setIsBg(true);
            mMenuAdapter.notifyDataSetChanged();
            selectItem(position);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
//        MenuInflater inflater = this.getMenuInflater();
//        inflater.inflate(R.menu.menu_setting, menu);
//        final SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
//        searchView.setQueryHint("Search");
//        searchView.setIconified(true);
//        mSearchItem = menu.findItem(R.id.menu_search);
//        mSearchItem.setActionView(searchView).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
//        mSearchItem.setVisible(true);
//        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
//            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//            @Override
//            public void onFocusChange(View view,
//                                      boolean queryTextFocused) {
//                if (!queryTextFocused) {
//                    mSearchItem.collapseActionView();
//                    searchView.setQuery("", false);
//                }
//            }
//        });
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//            @Override
//            public boolean onQueryTextSubmit(final String searchKeyword) {
//                //검색어넘김
//                if(!DsObjectUtils.getInstance(getApplicationContext()).isEmpty(searchKeyword)){
//                    Intent NextIntent = new Intent(getApplicationContext(), SearchActivity.class);
//                    NextIntent.putExtra("search", searchKeyword);
//                    startActivity(NextIntent);
//                    mSearchItem.collapseActionView();
//                    searchView.setQuery("", false);
//                }else{
//                    Toast.makeText(getApplicationContext(), R.string.search_not, Toast.LENGTH_SHORT).show();
//                }
//
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return true;
//            }
//        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
