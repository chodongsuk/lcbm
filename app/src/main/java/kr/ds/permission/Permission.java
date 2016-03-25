package kr.ds.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Administrator on 2016-02-26.
 */
    public class Permission implements PermissionLp {

    //위치 퍼미션
    public final static String[] LOCATIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    //전화 퍼미션
    public final static String[] CALLS = {Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE};

    //저장공간 퍼미션
    public final static String[] STORAGES = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    //주소록 퍼미션
    public final static String[] ACCOUNTS = {Manifest.permission.GET_ACCOUNTS};

    private Activity mActivity;
    private PermissionListener mPermissionListener;
    public Permission(Activity activity) {
        mActivity = activity;
    }
    public Permission setCallback(PermissionListener permissionListener){
        mPermissionListener = permissionListener;
        return this;
    }
    public Permission isLocation(){
        if(mPermissionListener != null) {
            if (!hasPermission(LOCATIONS[0]) && !hasPermission(LOCATIONS[1])) {
                mPermissionListener.requestPermissions(LOCATIONS);
            } else {
                mPermissionListener.onSuccess();
            }
        }
        return this;
    }

    public Permission isCall(){
        if(mPermissionListener != null) {
            if (!hasPermission(CALLS[0]) && !hasPermission(CALLS[1])) {
                mPermissionListener.requestPermissions(CALLS);
            } else {
                mPermissionListener.onSuccess();
            }
        }
        return this;
    }

    public Permission isStorage(){
        if(mPermissionListener != null) {
            if (!hasPermission(STORAGES[0]) && !hasPermission(STORAGES[1])) {
                mPermissionListener.requestPermissions(STORAGES);
            } else {
                mPermissionListener.onSuccess();
            }
        }
        return this;
    }
    public Permission isaccount(){
        if(mPermissionListener != null) {
            if (!hasPermission(ACCOUNTS[0])) {
                mPermissionListener.requestPermissions(ACCOUNTS);
            } else {
                mPermissionListener.onSuccess();
            }
        }
        return this;
    }

    @Override
    public boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED== ActivityCompat.checkSelfPermission(mActivity, perm));
    }

    public int requestCode;
    public String[] permissions;
    public int[] grantResults;

    public int getRequestCode() {
        return requestCode;
    }
    public String[] getPermissions() {
        return permissions;
    }
    public int[] getGrantResults() {
        return grantResults;
    }
    public void setRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        this.requestCode = requestCode;
        this.permissions = permissions;
        this.grantResults = grantResults;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(mPermissionListener != null){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPermissionListener.onSuccess();
            }else{
                mPermissionListener.onCancle();
            }
        }
    }
    public interface PermissionListener{
        public void onSuccess();
        public void onCancle();
        public void requestPermissions(String type[]);
    }

}


