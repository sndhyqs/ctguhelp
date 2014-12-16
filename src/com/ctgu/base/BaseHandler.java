/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package com.ctgu.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;

public class BaseHandler extends Handler {
	protected BaseActivity activity;

	public BaseHandler(BaseActivity activity) {
		this.activity = activity;
	}

	public BaseHandler(Looper looper) {
		super(looper);
	}

	public void handleMessage(Message msg) {
		int taskId = msg.getData().getInt("taskId");
		switch (msg.what) {
		case BaseTask.TASK_COMPLETE:

			Object result = msg.obj;
			if (result != null) {
				this.activity.onTaskComplete(taskId, result);
				Log.i("处理id", String.valueOf(taskId));
				break;
			}else{
				activity.onTaskComplete(taskId);
			}
			break;
		case BaseTask.NETWORK_ERROR:

			int taskId1 = msg.getData().getInt("task");
			activity.onNetworkError(taskId1);
			break;
		case 3:

			Object obj = (String) msg.obj;
			this.activity.onTaskComplete(taskId, obj);
			break;
		case 4:
			String result1 = (String) msg.obj;
			activity.toast((String) result1);
			break;
		}
	}
}