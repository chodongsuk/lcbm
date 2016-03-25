package kr.com.lcbm;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.ds.adapter.MenuAdapter;
import kr.ds.fragment.BaseFragment;
import kr.ds.fragment.CategoryFragment;
import kr.ds.handler.MenuHandler;
import kr.ds.permission.Permission;
import kr.ds.utils.DsObjectUtils;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mListView;
    private Toolbar mToolbar;

    private FragmentManager mFm;
    private FragmentTransaction mFt;
    private Fragment mFragment = null;

    private String[] mFragmentTitles;
    private ArrayList<MenuHandler> mMenuData;
    private MenuHandler mMenuHandler;


    private int mMenuPosition = -1; //디폴트
    private MenuAdapter mMenuAdapter;
    private Bundle mBundle = null;

    private MenuItem mSearchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            mBundle = savedInstanceState;
            mMenuPosition = savedInstanceState.getInt("menu_position");
        }
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mListView = (ListView) findViewById(R.id.navdrawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(R.drawable.side_menu);
            mToolbar.setTitle(R.string.app_name);
            setSupportActionBar(mToolbar);
        }
        mDrawerToggle = new ToolbarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //메뉴
        mFragmentTitles = getResources().getStringArray(R.array.fragments);
        mMenuData = new ArrayList<MenuHandler>();
        mMenuHandler = new MenuHandler();
        for(int i=0; i< mFragmentTitles.length; i++) {
            mMenuData.add(new MenuHandler());
            mMenuHandler = mMenuData.get(mMenuData.size()-1);
            mMenuHandler.setName(mFragmentTitles[i].toString());
            mMenuHandler.setBg(false);
        }

        mMenuAdapter = new MenuAdapter(getApplicationContext(),mMenuData);
        mListView.setOnItemClickListener(new DrawerItemClickListener());
        mListView.setAdapter(mMenuAdapter);

        if(mMenuPosition != -1){//메뉴설정
            mMenuData.get(mMenuPosition).setBg(true);
            mMenuAdapter.notifyDataSetChanged();
        }
        if (savedInstanceState == null) {
            selectItem(0);
        }
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
        switch(position){
            case 0:
                mFragment = CategoryFragment.newInstance();
                setFragment(mFragment);
                break;
        }


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
                mMenuData.get(i).setBg(false);
            }
            mMenuData.get(position).setBg(true);
            mMenuAdapter.notifyDataSetChanged();
            selectItem(position);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_setting, menu);
        final SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
        searchView.setQueryHint("Search");
        searchView.setIconified(true);
        mSearchItem = menu.findItem(R.id.menu_search);
        mSearchItem.setActionView(searchView).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        mSearchItem.setVisible(true);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onFocusChange(View view,
                                      boolean queryTextFocused) {
                if (!queryTextFocused) {
                    mSearchItem.collapseActionView();
                    searchView.setQuery("", false);
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public boolean onQueryTextSubmit(final String searchKeyword) {
                //검색어넘김
                if(!DsObjectUtils.isEmpty(searchKeyword)){
                    Intent NextIntent = new Intent(getApplicationContext(), SearchActivity.class);
                    NextIntent.putExtra("search", searchKeyword);
                    startActivity(NextIntent);
                    mSearchItem.collapseActionView();
                    searchView.setQuery("", false);
                }else{
                    Toast.makeText(getApplicationContext(), R.string.search_not, Toast.LENGTH_SHORT).show();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
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
