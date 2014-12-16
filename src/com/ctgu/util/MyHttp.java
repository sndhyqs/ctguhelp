package com.ctgu.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import java.io.InputStream;
import android.util.Log;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.apache.http.HttpEntity;

import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;

import java.util.Set;

public class MyHttp {
	HttpResponse httpResponse;

	public InputStream Get(HttpClient client, String path) throws Exception {
		Log.i("myhttp", "httpGET提交开始");
		InputStream is = null;
		 Log.i("提交地址", path);
		HttpGet httpGet = new HttpGet(path);
		try {
			httpResponse = client.execute(httpGet);
		//	Header[] header = httpResponse.getAllHeaders();
			//Log.i("myhttp", httpResponse.getStatusLine().getStatusCode());
			Log.i("myhttp", "httpGET提交中");
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				is = httpResponse.getEntity().getContent();
			 Log.i("myhttp", "httpGET提交成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("网络状况比较差，请稍后重试");
		}

		return is;
	}

	public InputStream Post(HttpClient client, String path, Map urlParams) throws Exception {
		Log.i("myhttp", "httpPOST提交开始");
		InputStream is = null;
		HttpPost httpPost = new HttpPost(path);
		Log.i("提交地址", path);
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		// get post parameters
		Iterator it = urlParams.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			postParams.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
		}

		httpPost.setEntity(new UrlEncodedFormEntity(postParams, "utf-8"));

		// Log.w("AppClient.post.url", path);
		// Log.w("AppClient.post.data", postParams.toString());
		// send post request
		try {
			HttpResponse httpResponse = client.execute(httpPost);
			Log.i("myhttp返回码", String.valueOf(httpResponse.getStatusLine().getStatusCode()));
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				is = httpResponse.getEntity().getContent();
				Log.i("myhttp", "httpPost提交成功");
				return is;
			} else {
				return null;
			}
		} catch (ConnectTimeoutException e) {
			throw new Exception("网络超时");
		} catch (Exception e1) {
			throw new Exception("网络错误");
		}
	}
}
