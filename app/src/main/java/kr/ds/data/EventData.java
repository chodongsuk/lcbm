package kr.ds.data;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import kr.ds.asynctask.DsAsyncTask;
import kr.ds.asynctask.DsAsyncTaskCallback;
import kr.ds.config.Config;
import kr.ds.handler.EventHandler;
import kr.ds.httpclient.DsHttpClient;
import kr.ds.utils.DsObjectUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

/**
 * Created by Administrator on 2016-03-08.
 */
public class EventData extends BaseData {
    private Context mContext;
    private String URL = Config.URL + Config.URL_XML + Config.EVENT;
    private String PARAM = "";

    private EventHandler mEventHandler;
    private ArrayList<EventHandler> mData;

    private BaseResultListener mResultListener;

    public EventData(Context context){
        mContext = context;
    }
    @Override
    public BaseData clear() {
        if (mData != null) {
            mData = null;
        }
        mData = new ArrayList<EventHandler>();
        if (mEventHandler != null) {
            mEventHandler = null;
        }
        mEventHandler = new EventHandler();
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
                            mData.add(new EventHandler());
                            if (mData.size() > 0) {
                                mEventHandler = mData.get(mData.size() - 1);
                                mEventHandler.setEd_uid(jsonArray1.getJSONObject(i).getString("ed_uid"));
                                mEventHandler.setEd_name(jsonArray1.getJSONObject(i).getString("ed_name"));
                                mEventHandler.setEd_subject(jsonArray1.getJSONObject(i).getString("ed_subject"));
                                mEventHandler.setEd_image(jsonArray1.getJSONObject(i).getString("ed_image"));
                                mEventHandler.setEd_content(jsonArray1.getJSONObject(i).getString("ed_content"));
                                mEventHandler.setEd_regdate(jsonArray1.getJSONObject(i).getString("ed_regdate"));
                                mEventHandler.setSub_images(jsonArray1.getJSONObject(i).getString("sub_images"));
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
