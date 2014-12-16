/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.ctgu.base;

import org.apache.http.client.HttpClient;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.io.InputStream;

import com.ctgu.serve.MyServe;
import com.ctgu.util.MyHttp;
import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseTaskPool {
    private HttpClient client;
    private Context context;
    private static ExecutorService taskPool;
    private MyHttp myhttp = new MyHttp();
    private String basePath;
    
    public BaseTaskPool(BaseActivity ui,String basePath ,HttpClient client) {
        context = ui.getContext();
        taskPool = Executors.newCachedThreadPool();
        this.basePath = basePath;
        this.client = client;
    }
    
    public HttpClient getClient() {
        return client;
    }
    
    public void setClient(HttpClient client) {
       this. client = client;
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
            this.taskUrl = basePath+""+taskUrl;
            this.taskArgs = taskArgs;
            this.baseTask = baseTask;
            this.delayTime = delayTime;
        }
        
        public void run() {
            try {
                baseTask.onStart();
                InputStream httpResult = null;
                if(delayTime > 0) {
                    Thread.sleep(delayTime);
                }
                try {
                    if(taskUrl != null) {
                        if(taskArgs == null) {
                            httpResult = myhttp.Get(client, taskUrl);
                        } else {
                            httpResult = myhttp.Post(client, taskUrl, taskArgs);
                        }
                    }
                    if(httpResult != null) {
                        baseTask.onComplete(httpResult);
                    } else {
                        baseTask.onComplete();
                    }
                } catch(Exception e) {
                	MyServe server = new MyServe();
                	server.setCtgu_login(false);
                	myhttp.Get(client, basePath+C.api.loginOut);
                    e.printStackTrace();
                    Log.w("线程", "我错了");
                    baseTask.onError(e.getMessage());
                }
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    baseTask.onStop();
                    return;
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}