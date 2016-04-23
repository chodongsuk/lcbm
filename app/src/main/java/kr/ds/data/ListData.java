package kr.ds.data;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import kr.ds.asynctask.DsAsyncTask;
import kr.ds.asynctask.DsAsyncTaskCallback;
import kr.ds.config.Config;
import kr.ds.handler.ListHandler;
import kr.ds.httpclient.DsHttpClient;
import kr.ds.utils.DsObjectUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

/**
 * Created by Administrator on 2016-03-08.
 */
public class ListData extends BaseData {
    private Context mContext;
    private String URL = Config.URL + Config.URL_XML + Config.LIST;
    private String PARAM = "";

    private ListHandler mListHandler;
    private ArrayList<ListHandler> mData;

    private BaseResultListener mResultListener;

    public ListData(Context context){
        mContext = context;
    }
    @Override
    public BaseData clear() {
        if (mData != null) {
            mData = null;
        }
        mData = new ArrayList<ListHandler>();
        if (mListHandler != null) {
            mListHandler = null;
        }
        mListHandler = new ListHandler();
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
                            mData.add(new ListHandler());
                            if (mData.size() > 0) {
                                mListHandler = mData.get(mData.size() - 1);
                                mListHandler.setGd_uid(jsonArray1.getJSONObject(i).getString("gd_uid"));
                                mListHandler.setCd_code(jsonArray1.getJSONObject(i).getString("cd_code"));
                                mListHandler.setGd_name(jsonArray1.getJSONObject(i).getString("gd_name"));
                                mListHandler.setGd_tema(jsonArray1.getJSONObject(i).getString("gd_tema"));
                                mListHandler.setGd_address(jsonArray1.getJSONObject(i).getString("gd_address"));
                                mListHandler.setGd_tell(jsonArray1.getJSONObject(i).getString("gd_tell"));
                                mListHandler.setGd_time(jsonArray1.getJSONObject(i).getString("gd_time"));
                                mListHandler.setGd_holiday(jsonArray1.getJSONObject(i).getString("gd_holiday"));
                                mListHandler.setGd_image(jsonArray1.getJSONObject(i).getString("gd_image"));
                                mListHandler.setGd_lat(jsonArray1.getJSONObject(i).getString("gd_lat"));
                                mListHandler.setGd_lon(jsonArray1.getJSONObject(i).getString("gd_lon"));
                                mListHandler.setGd_content(jsonArray1.getJSONObject(i).getString("gd_content"));
                                mListHandler.setGd_regdate(jsonArray1.getJSONObject(i).getString("gd_regdate"));
                                mListHandler.setSub_images(jsonArray1.getJSONObject(i).getString("sub_images"));

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
