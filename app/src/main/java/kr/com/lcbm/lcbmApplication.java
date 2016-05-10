package kr.com.lcbm;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tsengvn.typekit.Typekit;


public class lcbmApplication extends Application{
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		Typekit.getInstance()
				.addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunGothic.ttf"))
				.addBold(Typekit.createFromAsset(this, "fonts/NanumBarunGothicBold.ttf"));


		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.delayBeforeLoading(100).cacheInMemory().cacheOnDisc().build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(
				defaultOptions).build();
		ImageLoader.getInstance().init(config);
	}
	

}
