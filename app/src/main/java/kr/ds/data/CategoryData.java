package kr.ds.data;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import kr.ds.asynctask.DsAsyncTask;
import kr.ds.asynctask.DsAsyncTaskCallback;
import kr.ds.config.Config;
import kr.ds.handler.CategoryHandler;
import kr.ds.httpclient.DsHttpClient;
import kr.ds.utils.DsObjectUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

/**
 * Created by Administrator on 2016-03-08.
 */
public class CategoryData extends BaseData {
    private Context mContext;
    private String URL = Config.URL + Config.URL_XML + Config.CATEGORY;
    private String PARAM = "";

    private CategoryHandler mCategoryHandler;
    private ArrayList<CategoryHandler> mData;

    private BaseResultListener mResultListener;

    public CategoryData(Context context){
        mContext = context;
    }
    @Override
    public BaseData clear() {
        if (mData != null) {
            mData = null;
        }
        mData = new ArrayList<CategoryHandler>();
        if (mCategoryHandler != null) {
            mCategoryHandler = null;
        }
        mCategoryHandler = new CategoryHandler();
        return this;
    }

    @Override
    public BaseData setUrl(String url) {
        if(DsObjectUtils.isEmpty(URL)){
            URL = url;
        }
        return this;
    }

    @Override
    public BaseData setParam(String param) {
        PARAM = param;
        return this;
    }

    @Override
    public BaseData getView() {
        new DsAsyncTask<String>().setCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {

                String content = new DsHttpClient().HttpGet(URL + PARAM, "euc-kr");
                JSONObject jsonObject = new JSONObject(content);
                JSONObject summeryjsonObject = jsonObject.getJSONObject("summery");
                String result = summeryjsonObject.getString("result");
                Log.i("TEST",jsonObject+"");
                if (result.matches("success")) {
                    JSONArray jsonArray1 = jsonObject.getJSONArray("list");
                    for(int i = 0; i < jsonArray1.length(); i++){
                            mData.add(new CategoryHandler());
                            if (mData.size() > 0) {
                                mCategoryHandler = mData.get(mData.size() - 1);
                                mCategoryHandler.setCode(jsonArray1.getJSONObject(i).getString("code"));
                                mCategoryHandler.setName(jsonArray1.getJSONObject(i).getString("name"));
                                mCategoryHandler.setImage(jsonArray1.getJSONObject(i).getString("image"));
                                mCategoryHandler.setType(jsonArray1.getJSONObject(i).getString("type"));
                                mCategoryHandler.setHomepage(jsonArray1.getJSONObject(i).getString("homepage"));
                                mCategoryHandler.setIsBg(false);
                            }
                    }
                }
                return result;
            }
        }).setCallback(new DsAsyncTaskCallback<String>() {
            @Override
            public void onPreExecute() {
            }
            @Override
            public void onPostExecute(String result) {
                if (result.matches("success") && mData.size() > 0) {
                    if (mResultListener != null) {
                        mResultListener.OnComplete(mData);
                    }
                } else {
                    if (mResultListener != null) {
                        mResultListener.OnError("result_error");
                    }
                }
            }
            @Override
            public void onCancelled() {
            }
            @Override
            public void Exception(Exception e) {
                if (mResultListener != null) {
                    mResultListener.OnError(e.getMessage() + "");
                }
            }
        }).execute();
        return this;
    }

    @Override
    public <T> BaseData getViewPost(T post) {
        return this;
    }

    @Override
    public <T> BaseData setCallBack(T resultListener) {
        mResultListener = (BaseResultListener) resultListener;
        return this;
    }
}
