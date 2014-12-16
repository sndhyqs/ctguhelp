package com.example.login;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ctgu.base.BaseTask;
import com.ctgu.base.BaseUiAuth;
import com.ctgu.base.C;
import com.ctgu.ctguhelp.R;
import com.ctgu.serve.MyServe;
import com.ctgu.util.IO;

public class Config extends BaseUiAuth {
	TextView zhanghao;
	RelativeLayout delete_course;
	RelativeLayout logout_ctgu;
	RelativeLayout about_us;
	RelativeLayout new_viersion, logout_ctgu_number;
	Button my_logout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		init();

	}

	private void init() {
		zhanghao = (TextView) findViewById(R.id.c_zhanghao);
		delete_course = (RelativeLayout) findViewById(R.id.c_delete_course);
		logout_ctgu = (RelativeLayout) findViewById(R.id.c_logout_ctgu);
		logout_ctgu_number = (RelativeLayout) findViewById(R.id.c_logout_ctgu_number);
		about_us = (RelativeLayout) findViewById(R.id.c_about_us);
		new_viersion = (RelativeLayout) findViewById(R.id.c_new_viersion);
		my_logout = (Button) findViewById(R.id.c_my_logout);

		File my_user = new File(getCacheDir(), "my_user.txt");
		if (my_user.exists()) {

			String name = IO.readFile(my_user);
			zhanghao.setText(name);
			zhanghao.setGravity(Gravity.CENTER);
		}

		logout_ctgu_number.setOnClickListener(new onClick());
		delete_course.setOnClickListener(new onClick());
		logout_ctgu.setOnClickListener(new onClick());
		about_us.setOnClickListener(new onClick());
		new_viersion.setOnClickListener(new onClick());

		my_logout.setOnClickListener(new onClick());
	}

	class onClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.c_delete_course:
				File course = new File(getCacheDir(), "course.xml");
				if (course.delete()) {
					toast("删除当前课表成功");
				} else {
					toast("当前并没有保存课表");
				}

				break;
			case R.id.c_logout_ctgu:
				if (my_serve.isCtgu_login()) {
					taskPool.addTask(C.task.loginOut, C.api.loginOut, new BaseTask() {
					}, 0);
					my_serve.setCtgu_login(false);
					toast("成功退出教务处");
				} else {
					toast("你并没有登陆教务处");
				}
				break;
			case R.id.c_about_us:
				forward_not_finish(About_Us.class);
				break;
			case R.id.c_logout_ctgu_number:
				File user = new File(getCacheDir(), "user.txt");
				if (user.delete()) {
					forward(UserLogin.class);
					toast("清除学号和密码成功");
				} else {
					toast("并没有记住学号和密码");
				}
				break;
			case R.id.c_my_logout:
				File myUser = new File(getCacheDir(), "my_user.txt");
				if (myUser.delete()) {
					Intent my_serve_intent = new Intent(Config.this, MyServe.class);
					stopService(my_serve_intent);
					forward(UserLogin.class);
					toast("退出当前用户成功");
				} else {
					toast("用户退出失败，请清除缓存");
				}
				break;
			}

		}

	}

}
