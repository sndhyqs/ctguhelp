package com.ctgu.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import org.apache.http.client.HttpClient;

import com.ctgu.model.User;
import com.ctgu.serve.MyServe;
import android.app.ProgressDialog;
import com.ctgu.util.NewTaskPool;

import java.io.InputStream;
import java.util.HashMap;
import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class BaseActivity extends Activity {
	protected HttpClient client;
	protected BaseHandler handler;
	protected MyServe my_serve;
	protected ProgressDialog pd;
	protected BaseTaskPool taskPool;
	protected NewTaskPool taskPool_ctguHelp;
	protected NewTaskPool task_pool_get_lost_find;
	protected NewTaskPool tt;
	protected User user;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		my_serve = new MyServe();
		init();
	}

	@SuppressLint("NewApi")
	private void init() {
		handler = new BaseHandler(this);
		tt = new NewTaskPool(this, null);
		taskPool = new BaseTaskPool(this, C.api.baseCtgu, my_serve.getCtgu_client());
		taskPool_ctguHelp = new NewTaskPool(this, C.api.baseCtguHelp);
		task_pool_get_lost_find = new NewTaskPool(this, C.api.bbangnetBase);
		pd = new ProgressDialog(this);
		user = new User(this);
		if (user.getMy_userName().isEmpty()) {
			my_serve.setCtguhelp_login(true);
			
		}
		pd.setMessage("正在加载中......");
	}

	public Context getContext() {
		return this;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(BaseHandler handler) {
		this.handler = handler;
	}

	public void toast(String msg) {
		Toast.makeText(this, msg, 0x0).show();
	}

	public void forward_not_finish(Class<?> classObj) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		startActivity(intent);
	}

	public void forward_not_finish(Class<?> classObj, Bundle params) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.putExtras(params);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		startActivity(intent);
	}

	public void forward(Class<?> classObj) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		startActivity(intent);
		finish();
	}

	public void forward(Class<?> classObj, Bundle params) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.putExtras(params);
		startActivity(intent);
	}

	public void dofinish() {
		finish();
	}

	public BaseTaskPool getTaskPool() {
		return taskPool;
	}

	public void sendMessage(int what) {
		Message m = Message.obtain();
		m.what = what;
		handler.sendMessage(m);
	}

	public void sendMessage(int what, Object data) {
		Message m = Message.obtain();
		m.what = what;
		m.obj = data;
		handler.sendMessage(m);
	}

	public void sendMessage(int what, int taskId, Object data) {
		Bundle b = new Bundle();
		b.putInt("taskId", taskId);
		Message m = Message.obtain();
		m.what = what;
		m.setData(b);
		m.obj = data;
		handler.sendMessage(m);
	}

	public void doTaskAsync(int taskId, int delayTime) {
		taskPool.addTask(taskId, new BaseTask() {
			@Override
			public void onError(String error) {
			}

			@Override
			public void onComplete(InputStream inputStream) {
			}
		}, delayTime);
	}

	public void doTaskAsync(int taskId, BaseTask baseTask, int delayTime) {
		tt.addTask(taskId, baseTask, delayTime);
	}

	public void doTaskAsync(int taskId, String taskUrl) {

	}

	public void doTaskAsync(String taskId, String baseUrl, HashMap<String, String> taskUrl, HashMap taskArgs) {

	}

	public void onTaskComplete(int taskId, Object message) {
	}

	public void onTaskComplete(int taskId) {
	}

	public void onNetworkError(int taskId) {
		if (pd.isShowing()) {
			pd.dismiss();
		}
		toast(C.err.network);
	}
}
