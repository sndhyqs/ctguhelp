package com.ctgu.util;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.io.InputStream;

import com.ctgu.base.BaseActivity;
import com.ctgu.base.BaseTask;
import com.ctgu.util.MyHttp;
import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewTaskPool {
	private String base_path;
	private HttpClient client;
	private Context context;
	private static ExecutorService taskPool;
	private MyHttp myhttp = new MyHttp();

	public NewTaskPool(BaseActivity ui, String path) {
		context = ui.getContext();
		taskPool = Executors.newCachedThreadPool();
		this.client = new DefaultHttpClient();
		base_path = path;
	}

	public void addTask(int taskId, String taskUrl, HashMap<String, String> taskArgs, BaseTask baseTask, int delayTime) {
		baseTask.setId(taskId);
		try {
			taskPool.execute(new TaskThread(client, context, taskUrl, taskArgs, baseTask, delayTime));
		} catch (Exception e) {
			taskPool.shutdown();
		}
	}

	public void addTask(int taskId, String taskUrl, BaseTask baseTask, int delayTime) {
		baseTask.setId(taskId);
		try {
			taskPool.execute(new TaskThread(client, context, taskUrl, null, baseTask, delayTime));
		} catch (Exception e) {
			taskPool.shutdown();
		}
	}

	public void addTask(int taskId, BaseTask baseTask, int delayTime) {
		baseTask.setId(taskId);
		try {
			taskPool.execute(new TaskThread(client, context, null, null, baseTask, delayTime));
		} catch (Exception e) {
			taskPool.shutdown();
		}
	}

	class TaskThread implements Runnable {
		private BaseTask baseTask;
		private HttpClient client;
		private Context context;
		private Map<String, String> taskArgs;
		private String taskUrl;
		private int delayTime = 0;

		public TaskThread(HttpClient client, Context context, String taskUrl, Map<String, String> taskArgs, BaseTask baseTask, int delayTime) {
			this.client = client;
			this.context = context;
			this.taskArgs = taskArgs;
			this.baseTask = baseTask;
			this.delayTime = delayTime;
			if (base_path != null) {
				this.taskUrl = base_path + "" + taskUrl;
			} else {
				base_path = taskUrl;
			}
		}

		public void run() {
			try {
				baseTask.onStart();
				InputStream is = null;
				if (delayTime > 0) {
					Thread.sleep(delayTime);
				}
				try {
					if (taskUrl != null) {
						if (taskArgs == null) {
							is = myhttp.Get(client, taskUrl);
						} else {
							is = myhttp.Post(client, taskUrl, taskArgs);
						}
					}
					if (is != null) {
						baseTask.onComplete(is);
					} else {
						baseTask.onComplete();
					}
				} catch (Exception e) {
					baseTask.onError(e.getMessage());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					baseTask.onStop();
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
