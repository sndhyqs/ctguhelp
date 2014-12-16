package com.ctgu.base;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ctgu.util.AppUtil;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BaseService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public void doTaskAsync(final int taskId, final String taskUrl) {

		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(new Runnable() {
			@Override
			public void run() {

				try {
					onTaskComplete(taskId, AppUtil.getMessage(null));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	public void doTaskAsync(final int taskId, final String taskUrl, final HashMap<String, String> taskArgs) {

		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(new Runnable() {
			@Override
			public void run() {

				try {
					onTaskComplete(taskId, AppUtil.getMessage(null));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	public void onTaskComplete(int taskId, BaseMessage message) {

	}

}