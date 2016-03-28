package kr.ds.config;

import android.content.Context;

public class Config {
	public Context mContext;
	
	public static String URL = "http://lcbm.cafe24.com/";
	public static String URL_XML = "json/";
	public static String CATEGORY = "category.php";
	public static String EVENT = "event.php";
	public static String LIST = "list.php";
	public static String FILEFOLDER = "";// 서버파일폴더

	public Config(Context context) {
		mContext = context;
		FILEFOLDER = mContext.getExternalFilesDir(null).getAbsolutePath() + "/capture/";// 저장폴더
	}



	 

}
