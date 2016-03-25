package kr.ds.data;

import java.util.ArrayList;

public interface BaseResultListener {
	public <T> void OnComplete();
	public <T> void OnComplete(ArrayList<T> arrayList);
	public void OnError(String str);


}
