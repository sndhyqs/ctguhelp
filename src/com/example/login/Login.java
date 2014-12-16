package com.example.login;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ctgu.base.BaseActivity;
import com.ctgu.base.BaseTask;
import com.ctgu.base.C;
import com.ctgu.base.C.task;
import com.ctgu.ctguhelp.R;
import com.ctgu.util.IO;
import com.ctgu.util.XmlToString;

public class Login extends BaseActivity {
	Button app_login_btn_submit;
	EditText app_login_edit_name;
	EditText app_login_edit_pass;
	Bitmap bitmap;
	AlertDialog.Builder builder;
	ImageView checkCodeImage;
	private LayoutInflater inflater;
	EditText yanzhengma;
	private String userNumber, userPassword;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		inflater = LayoutInflater.from(this);
		app_login_edit_name = (EditText) findViewById(R.id.app_login_edit_name);
		app_login_edit_pass = (EditText) findViewById(R.id.app_login_edit_pass);
		app_login_btn_submit = (Button) findViewById(R.id.app_login_btn_submit);
		if (user.getUser() != null) {
			app_login_edit_name.setText(user.getUserNumber());
			app_login_edit_pass.setText(user.getPassword());
		}
	}

	@SuppressLint("NewApi")
	public void click(View v) {
		if (app_login_edit_name.getText().toString().trim().isEmpty() || app_login_edit_pass.getText().toString().trim().isEmpty()) {
			toast("学号或者密码不能为空");
		} else {
			loadCheckImage();
			userNumber = app_login_edit_name.getText().toString().trim();
			userPassword = app_login_edit_pass.getText().toString().trim();
			View vss = inflater.inflate(R.layout.login_dialog, null);
			checkCodeImage = (ImageView) vss.findViewById(R.id.checkCodeImage);
			yanzhengma = (EditText) vss.findViewById(R.id.yanzhengma);
			builder = new AlertDialog.Builder(this);
			builder.setTitle("验证码").setView(vss);
			builder.setPositiveButton("登陆", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					HashMap<String, String> taskArgs = new HashMap<String, String>();
					taskArgs.put("__EVENTVALIDATION", C.api.__EVENTVALIDATION);
					taskArgs.put("__VIEWSTATE", C.api.__VIEWSTATE);
					taskArgs.put("btnLogin.x", "23");
					taskArgs.put("btnLogin.y", "15");
					taskArgs.put("txtUserName", userNumber);
					taskArgs.put("txtPassword", userPassword);
					taskArgs.put("CheckCode", yanzhengma.getText().toString().trim());
					pd.show();
					taskPool.addTask(C.task.ctguLogin, C.api.ctguLogin, taskArgs, new BaseTask() {
						@Override
						public void onComplete(InputStream inputStream) {
							String result = IO.inputSreamToString(inputStream);

							sendMessage(BaseTask.TASK_COMPLETE, C.task.ctguLogin, result);
						}

						@Override
						public void onError(String error) {
							sendMessage(BaseTask.NETWORK_ERROR, C.task.userLogin, null);
						}
					}, 0);
				}
			});
			builder.show();
		}
	}

	public void loadCheckImage() {
		taskPool.addTask(C.task.checkCode, C.api.checkCode, new BaseTask() {

			@Override
			public void onComplete(InputStream inputStream) {
				Bitmap localBitmap = BitmapFactory.decodeStream(inputStream);
				sendMessage(BaseTask.TASK_COMPLETE, C.task.checkCode, localBitmap);
			}

			@Override
			public void onError(String error) {
				super.onError(error);
			}

		}, 0);
	}

	@SuppressLint("NewApi")
	public void onTaskComplete(int taskId, Object message) {
		switch (taskId) {
		case C.task.checkCode:
			Bitmap bitmap = (Bitmap) message;
			checkCodeImage.setImageBitmap(bitmap);
			break;

		case C.task.ctguLogin:
			if (pd.isShowing()) {
				pd.dismiss();
			}
			if (XmlToString.getStudentName((String) message).isEmpty()) {
				toast(XmlToString.getCtguLoginError((String) message));
			} else {
				loginMethod(XmlToString.getStudentName((String) message));
			}
			break;

		}
	}

	private void loginMethod(String name) {
		toast(name);
		user.setUserNumber(userNumber);
		user.setPassword(userPassword);
		user.setName(name);
		String myuser = user.getUserNumber() + "&" + user.getPassword() + "&" + user.getName();
		IO.saveToPackage(this, myuser, "user.txt");
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("user_number", user.getUserNumber());
		taskArgs.put("password", user.getPassword());
		taskArgs.put("name", user.getName());
		taskArgs.put("username", user.getMy_userName());
		taskPool_ctguHelp.addTask(C.task.set_password, C.api.set_password, taskArgs, new BaseTask() {
		}, 0);
		String success = "登陆成功";
		toast(success);
		my_serve.setCtgu_client(taskPool.getClient());
		my_serve.setCtgu_login(true);
		this.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			this.finish();
		}
		return false;
	}

}
