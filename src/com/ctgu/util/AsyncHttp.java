package com.ctgu.util;

import com.ctgu.base.C;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AsyncHttp {

	public static void Post(String url, RequestParams params, AsyncHttpResponseHandler handler) {
		AsyncHttpClient http = new AsyncHttpClient();
		url = C.api.baseCtguHelp + url;
		http.post(url, params, handler);
	}

	public static void Get(String url, AsyncHttpResponseHandler handler) {
		AsyncHttpClient http = new AsyncHttpClient();
		http.get(url, handler);

	}

}
