package com.ctgu.serve;

import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.client.HttpClient;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.IBinder;
import android.util.ArrayMap;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.ctgu.base.BaseService;
import com.ctgu.base.C;
import com.ctgu.ctguhelp.R;
import com.ctgu.util.IO;
import com.ctgu.util.MyHttp;
import com.example.login.MyMessage;
import com.example.login.TranionMyMessage;

public class MyServe extends BaseService {
	private static HttpClient ctgu_client = null;
	private static boolean ctgu_login = false;
	private static boolean ctguhelp_login = false;
	private static String userName = "";
	// Notification manager to displaying arrived push notifications
	private NotificationManager notiManager;

	// Thread Pool Executors
	private ExecutorService execService;

	public IBinder onBind(Intent intent) {
		return null;
	}

	// Loop getting notice
	private boolean runLoop = true;

	@Override
	public void onDestroy() {
		runLoop = false;
	}

	public void onCreate() {
		super.onCreate();
		ctgu_client = new DefaultHttpClient();
		notiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		execService = Executors.newSingleThreadExecutor();
		startService();
	}

	public void startService() {
		execService.execute(new Runnable() {
			@SuppressLint("NewApi")
			@Override
			public void run() {
				while (runLoop) {
					try {
						String url = C.api.baseCtguHelp + C.api.notice;
						doTaskAsync(C.task.notice, url);
						Thread.sleep(20 * 1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void showNotification(HashMap<String, String> data, int type) {
		try {
			Notification n = new Notification();
			n.flags |= Notification.FLAG_SHOW_LIGHTS;
			n.flags |= Notification.FLAG_AUTO_CANCEL;
			n.defaults = Notification.DEFAULT_ALL;
			n.icon = R.drawable.ico;
			n.when = System.currentTimeMillis();
			PendingIntent pi = null;
			switch (type) {
			case 1:
				pi = PendingIntent.getActivity(this, 0, new Intent(this, MyMessage.class), 0);
				break;
			case 2:
				pi = PendingIntent.getActivity(this, 0, new Intent(this, TranionMyMessage.class), 0);
				break;
			}
			// Change the name of the notification here
			n.setLatestEventInfo(this, data.get("title"), data.get("content"), pi);
			// show notification
			notiManager.notify(1000, n);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doTaskAsync(final int taskId, final String taskUrl) {
		final HttpClient httpclient = new DefaultHttpClient();
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(new Runnable() {
			@Override
			public void run() {
				try {
					MyHttp client = new MyHttp();
					HashMap<String, String> taskArgs = new HashMap<String, String>();
					taskArgs.put("user", userName);
					InputStream httpResult = client.Post(httpclient, taskUrl, taskArgs);
					String rusult = IO.inputSreamToString(httpResult);
					if (rusult.equals("") || rusult == "") {

					} else {
						TaskComplete(taskId, rusult);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	public void TaskComplete(int taskId, String message) {

		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(message);
			String remind_read = (String) jsonObject.get("remind_read");
			if (remind_read.equals("1")) {
				String title = (String) jsonObject.get("title");
				String content = (String) jsonObject.get("content");
				String type = (String) jsonObject.get("type");
				HashMap<String, String> data = new HashMap<String, String>();
				data.put("title", title);
				data.put("content", content);
				showNotification(data, Integer.valueOf(type));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// 判断进程是否存在
	public boolean checkBrowser(String packageName) {
		packageName = "com.cgu.ctguhelp";
		if (packageName == null || "".equals(packageName))
			return false;
		try {
			ApplicationInfo info = getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	public HttpClient getCtgu_client() {
		return ctgu_client;
	}

	public void setCtgu_client(HttpClient ctgu_client) {
		this.ctgu_client = ctgu_client;
	}

	public boolean isCtgu_login() {
		return ctgu_login;
	}

	public void setCtgu_login(boolean ctgu_login) {
		this.ctgu_login = ctgu_login;
	}

	public boolean isCtguhelp_login() {
		return ctguhelp_login;
	}

	public void setCtguhelp_login(boolean ctguhelp_login) {
		this.ctguhelp_login = ctguhelp_login;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		MyServe.userName = userName;
	}
}
