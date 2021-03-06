/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package com.example.login;

import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import android.widget.TextView;
import java.util.ArrayList;

import com.ctgu.model.MyCourse;
import com.ctgu.util.IO;
import com.ctgu.util.NewTaskPool;
import com.ctgu.util.XmlToString;

import android.view.ViewGroup;

import com.ctgu.adapter.MyViewPagerAdapter;
import com.ctgu.base.BaseTask;
import com.ctgu.base.BaseUiAuth;
import com.ctgu.base.C;
import com.ctgu.ctguhelp.R;

import android.widget.Button;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.Display;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class My_course_table extends BaseUiAuth {
	private int bmpW;
	private ArrayList<MyCourse> course;
	private int currIndex;
	int date;
	private ImageView imageView;
	private int offset;
	private Button textView1;
	private Button textView2;
	private Button textView3;
	private Button textView4;
	private Button textView5;
	private Button textView6;
	private Button textView7;
	private View view1;
	private View view2;
	private View view3;
	private View view4;
	private View view5;
	private View view6;
	private View view7;
	private ViewPager viewPager;
	private List<View> views;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kebiao);
		get_kebiao();

	}

	private void init() {
		InitImageView();
		InitTextView();
		InitViewPager();
	}

	private void upload() {
		AsyncHttpClient client = new AsyncHttpClient();
		File file = new File(getCacheDir(), "course.xml");
		RequestParams params = new RequestParams();
		try {
			params.put("course", file);
			params.put("userNumber", user.getUserNumber());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		client.post("http://www.ctguhelp.com/ctguhelp/ctguhelp/up_load_course.php", params, new AsyncHttpResponseHandler() {

			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try {
					System.out.println(new String(responseBody, "utf-8"));
					return;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

			}
		});
	}

	private void get_kebiao() {

		File file = new File(getCacheDir(), "course.xml");
		if (!file.exists()) {
			if (my_serve.isCtgu_login()) {
				getCoureseOfCtgu();
			} else {
				forward_not_finish(Login.class);
			}
		} else {
			FileInputStream is = null;
			try {
				is = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			course = XmlToString.xml_to_mycourse(is);
			init();
		}

	}

	private void getCoureseOfCtgu() {
		pd.show();
		taskPool.addTask(C.task.getCourse, C.api.getCourse, new BaseTask() {

			@Override
			public void onComplete(InputStream inputStream) {
				FileOutputStream fout = null;
				String data = IO.inputSreamToString(inputStream);
				ArrayList<MyCourse> courseArray = XmlToString.courseHtmlToArray(data);
				File file = new File(getCacheDir(), "course.xml");
				try {
					fout = new FileOutputStream(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
				XmlToString.save_xml_course(courseArray, fout);
				sendMessage(BaseTask.TASK_COMPLETE, C.task.getCourse, "success");
			}

			@Override
			public void onError(String error) {

				sendMessage(BaseTask.TASK_COMPLETE, C.task.getCourse, null);
			}

		}, 0);
	}

	@Override
	public void onTaskComplete(int taskId, Object message) {
		switch (taskId) {
		case C.task.getCourse:
			if (pd.isShowing()) {
				pd.dismiss();
			}
			upload();
			get_kebiao();
			break;

		}
	}

	private void InitViewPager() {
		viewPager = (ViewPager) findViewById(R.id.vPager);
		views = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();
		view1 = inflater.inflate(R.layout.layout1, null);
		view2 = inflater.inflate(R.layout.layout1, null);
		view3 = inflater.inflate(R.layout.layout1, null);
		view4 = inflater.inflate(R.layout.layout1, null);
		view5 = inflater.inflate(R.layout.layout1, null);
		view6 = inflater.inflate(R.layout.layout1, null);
		view7 = inflater.inflate(R.layout.layout1, null);
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		views.add(view5);
		views.add(view6);
		views.add(view7);
		viewPager.setAdapter(new MyViewPagerAdapter(views, date, course));
		viewPager.setCurrentItem(0x0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener(this));
	}

	private void InitTextView() {
		textView1 = (Button) findViewById(R.id.riqi_1);
		textView2 = (Button) findViewById(R.id.riqi_2);
		textView3 = (Button) findViewById(R.id.riqi_3);
		textView4 = (Button) findViewById(R.id.riqi_4);
		textView5 = (Button) findViewById(R.id.riqi_5);
		textView6 = (Button) findViewById(R.id.riqi_6);
		textView7 = (Button) findViewById(R.id.riqi_7);
		textView1.setOnClickListener(new MyOnClick(0));
		textView2.setOnClickListener(new MyOnClick(1));
		textView3.setOnClickListener(new MyOnClick(2));
		textView4.setOnClickListener(new MyOnClick(3));
		textView5.setOnClickListener(new MyOnClick(4));
		textView6.setOnClickListener(new MyOnClick(5));
		textView7.setOnClickListener(new MyOnClick(6));
	}

	private void InitImageView() {
		imageView = (ImageView) findViewById(R.id.iv_bottom_line);
		bmpW = imageView.getLayoutParams().width;
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (((screenW / 7) - bmpW) / 2);
		Matrix matrix = new Matrix();
		matrix.postTranslate((float) offset, 0.0f);
		imageView.setImageMatrix(matrix);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(0x7f090000, menu);
		return true;
	}

	class MyOnClick implements View.OnClickListener {
		int index = 0x0;

		MyOnClick(int i) {
			index = i;
		}

		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}
	}

	class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
		int one;
		int two;

		MyOnPageChangeListener(My_course_table p1) {
			one = ((offset * 0x2) + bmpW);
			two = (offset * 0x2);
		}

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			TranslateAnimation animation = new TranslateAnimation((float) (one * currIndex), (float) (one * arg0), 0.0f, 0.0f);
			currIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(0x12c);
			imageView.startAnimation(animation);
		}
	}
}
