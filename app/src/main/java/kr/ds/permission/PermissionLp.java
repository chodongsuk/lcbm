package kr.ds.permission;

/**
 * Created by Administrator on 2016-02-26.
 */
public interface PermissionLp {

    public boolean hasPermission(String perm);
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) ;

}
