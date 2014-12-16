package com.example.login;

import android.view.View;

import com.ctgu.model.Course1;
import com.ctgu.model.MyCourse;
import com.ctgu.model.Score;
import com.ctgu.serve.MyServe;
import android.app.ProgressDialog;
import com.ctgu.base.BaseUiAuth;
import com.ctgu.base.C;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import android.widget.ListView;

import com.ctgu.util.IO;
import com.ctgu.util.MainTab;
import com.ctgu.util.NewTaskPool;
import com.ctgu.base.BaseTask;
import android.widget.ImageView;
import com.ctgu.base.BaseTaskPool;
import com.ctgu.ctguhelp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.KeyEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class My_Menu extends BaseUiAuth {
	TextView my_message_text;
	Handler handler;
	ListView lv_score;
	static My_Menu instance = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_meun);
		if (C.debug == true)
			Log.i("当前版本", String.valueOf(getLocalVersionCode(this)));
		instance = this;
		get_version_code();
		init();
	}

	private void init() {
		ImageView get_PingJiao = (ImageView) findViewById(R.id.pingjiao);
		ImageView get_Score = (ImageView) findViewById(R.id.score);
		ImageView bt_lost_found = (ImageView) findViewById(R.id.lost_found);
		ImageView library = (ImageView) findViewById(R.id.library);
		ImageView waimai = (ImageView) findViewById(R.id.waimai);
		ImageView news = (ImageView) findViewById(R.id.news);
		// my_message_text = (TextView) findViewById(R.id.library);

		get_PingJiao.setOnClickListener(new OnClick());
		get_Score.setOnClickListener(new OnClick());
		bt_lost_found.setOnClickListener(new OnClick());
		waimai.setOnClickListener(new OnClick());
		library.setOnClickListener(new OnClick());
		news.setOnClickListener(new OnClick());
		MainTab main = new MainTab(instance);
		// main.setMain_tab_unread_2(2);

	}

	public void onTaskComplete(int taskId, Object message) {
		if (pd.isShowing()) {
			pd.dismiss();
		}
		switch (taskId) {
		case C.task.get_version:
			String s = (String) message;
			String[] app = s.split("&");
			test_VersionCode(Integer.valueOf(app[1]));
			break;
		}
	}

	class OnClick implements View.OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.pingjiao:

				if (my_serve.isCtgu_login()) {
					forward_not_finish(List_Course.class);
				} else {
					forward_not_finish(Login.class);
				}
				break;
			case R.id.lost_found:
				forward_not_finish(Lost_Find.class);
				break;
			case R.id.score:
				if (my_serve.isCtgu_login()) {
					forward_not_finish(List_Score.class);
				} else {
					forward_not_finish(Login.class);
				}
				break;
			case R.id.library:
				forward_not_finish(LibraryIndex.class);
				break;
			case R.id.news:
				toast("我们正在努力开发中");
				break;
			case R.id.waimai:
				toast("我们正在努力开发中");
				// forward_not_finish(WaiMai.class);
				break;
			}
		}
	}

	private void get_version_code() {
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("user", user.getMy_userName());
		taskArgs.put("version", String.valueOf(getLocalVersionCode(this)));
		taskPool_ctguHelp.addTask(C.task.get_version, C.api.get_version, taskArgs, new BaseTask() {

			@Override
			public void onComplete(InputStream inputStream) {
				String data = IO.inputSreamToString(inputStream);
				sendMessage(BaseTask.TASK_COMPLETE, C.task.get_version, data);
			}

			@Override
			public void onError(String error) {

				sendMessage(BaseTask.TASK_COMPLETE, C.task.get_version, null);
			}

		}, 0);

	}

	public void test_VersionCode(int version) {
		if (version > getLocalVersionCode(this)) {
				toast("已经有新版了，可以去各大手机助手下载哟！");
		}
	}

	public int getLocalVersionCode(Context context) {
		PackageManager manager = this.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(this.getPackageName(), 0);
		} catch (NameNotFoundException e) {

			e.printStackTrace();
		}
		int version = info.versionCode;
		if (C.debug == true)
			Log.i("My_Menu版本", String.valueOf(version));
		return version;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == 0x4) && (event.getRepeatCount() == 0)) {
			dofinish();
		}
		return false;
	}
}
