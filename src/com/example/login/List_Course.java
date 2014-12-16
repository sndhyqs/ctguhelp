package com.example.login;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ctgu.adapter.CourseAdapter;
import com.ctgu.base.BaseActivity;
import com.ctgu.base.BaseHandler;
import com.ctgu.base.BaseTask;
import com.ctgu.base.C;
import com.ctgu.ctguhelp.R;
import com.ctgu.model.Course;
import com.ctgu.util.AsyncHttp;
import com.ctgu.util.IO;
import com.ctgu.util.Json;
import com.ctgu.util.XmlToString;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 评教内容列表页面和评教方法
 * 
 * @author 晏青山
 * 
 */
public class List_Course extends BaseActivity {
	private static List_Course instance = null;
	private String EVENTVALIDATION = "";// 评教必须提交的参数
	private String VIEWSTATE = "";// 评教必须提交的参数
	private Course course;
	private CourseAdapter courseAdapter;
	private EditText et_pingjiao_score;
	private LayoutInflater inflater = null;
	private ListView lv_score;
	private boolean pingjiao_Operate;
	private int pingjiao_Operate_Id;// 评教课程的id
	private int pingjiao_Course_Score;// 评教的分数
	private BaseHandler bh;
	private String checkBoxString = "RadioButton2";// 评教中是否推荐这位教师继续教这门课
	private String teacherChangeString = "";// 老师的优点
	private String teacherGoodString = "";// 老师需要改进

	@SuppressLint({ "NewApi" })
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.list_course);
		init();
		lv_score.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			private void get_Pingjiao_Id() {

				pingjiao_Operate = course.getPingjiaoOperateId().isEmpty();

				if (!pingjiao_Operate) {
					pingjiao_Operate_Id = Integer.parseInt(course.getPingjiaoOperateId());
				} else {
					pingjiao_Operate_Id = 0;
				}
			}

			@SuppressLint({ "NewApi" })
			public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
				course = ((Course) courseAdapter.getItem(paramAnonymousInt));
				get_Pingjiao_Id();
				if (pingjiao_Operate) {
					toast("您已经全都评教过了，不需要再评教了");
					return;
				}
				pingjiao_First();
			}
		});
	}

	/**
	 * 根据ID获取评教页面的内容 设置评教必备的参数 VIEWSTATE EVENTVALIDATION
	 */
	private void get_Pingjiao_Operate_Html() {
		taskPool.addTask(1004, "Stu_Assess/Stu_Assess_Proc.aspx?id=" + this.pingjiao_Operate_Id, new BaseTask() {
			public void onComplete(InputStream paramAnonymousInputStream) {
				Elements localElements = XmlToString.pingjiao_get_data(IO.inputSreamToString(paramAnonymousInputStream));
				VIEWSTATE = localElements.get(0).attr("value");
				EVENTVALIDATION = localElements.get(5).attr("value");
				sendMessage(0, getId(), localElements);
			}

			public void onError(String paramAnonymousString) {
				sendMessage(1, getId(), null);
			}
		}, 0);
	}

	/**
	 * 内容初始化 主要是获取评教的列表页面的内容
	 */
	private void init() {
		instance = this;
		inflater = LayoutInflater.from(this);// 为后面的对话框提供参数
		lv_score = ((ListView) findViewById(R.id.list));
		bh = new BaseHandler(this);
		pingjiao_get();
		courseAdapter = new CourseAdapter(this);
		lv_score.setAdapter(courseAdapter);
	}

	/**
	 * 获取评教后的页面是否是否需要刷新
	 */
	private void load_pingjiao() {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setMessage("是否刷新一下界面");
		localBuilder.setPositiveButton("是", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
				toast("正在刷新页面中......");
				pingjiao_get();
			}
		});
		localBuilder.setNegativeButton("否", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
			}
		});

		// 显示对话框
		localBuilder.show();
	}

	/**
	 * 评教列表页面的获取
	 */
	private void pingjiao_get() {
		pd.show();
		taskPool.addTask(C.task.getCourse, "Stu_Assess/Stu_Assess.aspx", new BaseTask() {
			public void onComplete(InputStream paramAnonymousInputStream) {
				String str = IO.inputSreamToString(paramAnonymousInputStream);
				ArrayList<Course> course = XmlToString.PingjiaoTO(str);
				sendMessage(BaseTask.TASK_COMPLETE, C.task.getCourse, course);
			}

			public void onError(String paramAnonymousString) {
				sendMessage(BaseTask.NETWORK_ERROR, C.task.getCourse, null);
			}
		}, 0);
	}

	/**
	 * 这个方法主要是因为对话框默认是点了确认就取消了，但是由于用户输入出错是不能够取消的，所以需要设置对话框 可以取消或者不可以取消
	 * 
	 * @param paramDialogInterface
	 */
	public void dailog_Can_Cancer(DialogInterface paramDialogInterface) {
		try {
			Field localField = paramDialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
			localField.setAccessible(true);
			localField.set(paramDialogInterface, Boolean.valueOf(true));
			return;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	public void dialg_Can_not_Cancer(DialogInterface paramDialogInterface) {
		try {
			Field localField = paramDialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
			localField.setAccessible(true);
			localField.set(paramDialogInterface, Boolean.valueOf(false));
			return;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	public void onTaskComplete(int paramInt, Object paramObject) {
		if (pd.isShowing()) {
			pd.dismiss();
		}
		switch (paramInt) {
		case C.task.getCourse:
			ArrayList<Course> ciourses = (ArrayList<Course>) paramObject;
			courseAdapter.clear();
			courseAdapter.addItems(ciourses);
			break;
		case C.task.postCourse:
			toast("评教成功");
			load_pingjiao();
			break;
		}

	}

	/**
	 * 评教第一步
	 */
	private void pingjiao_First() {
		get_Pingjiao_Operate_Html();
		View localView = this.inflater.inflate(R.layout.pingjiao_first, null);
		et_pingjiao_score = ((EditText) localView.findViewById(R.id.pingjiao_score));
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(instance);
		localBuilder.setTitle("评教第一步");
		localBuilder.setView(localView);
		localBuilder.setPositiveButton("下一步", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
				int i;
				try {
					int j = Integer.parseInt(et_pingjiao_score.getText().toString());

					i = j;
				} catch (Exception localException) {
					toast("请输入正确的分数");
					i = 0;
				}
				dialg_Can_not_Cancer(paramAnonymousDialogInterface);
				if ((i >= 1) && (i <= 5)) {
					pingjiao_Course_Score = i;
					dailog_Can_Cancer(paramAnonymousDialogInterface);
					pingjiaoSecond();
				} else {
					toast("请输入正确的分数");
				}
			}
		});
		localBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
				dailog_Can_Cancer(paramAnonymousDialogInterface);
			}
		});
		localBuilder.show();
	}

	/**
	 * 评教第二步
	 */
	private void pingjiaoSecond() {
		View localView = this.inflater.inflate(R.layout.pingjiao_second, null);
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(instance);
		final CheckBox checkBox = (CheckBox) localView.findViewById(R.id.checkBox);
		final EditText teacherGood = (EditText) localView.findViewById(R.id.teacheGood);
		final EditText teacherChange = (EditText) localView.findViewById(R.id.teacherChange);
		localBuilder.setTitle("评教第二步");
		localBuilder.setView(localView);
		localBuilder.setPositiveButton("下一步", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
				if (checkBox.isChecked()) {
					checkBoxString = "RadioButton1";
				}
				teacherChangeString = teacherChange.getText().toString().trim();
				teacherGoodString = teacherGood.getText().toString().trim();

				pingjiao_Third();
			}
		});
		localBuilder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
				dailog_Can_Cancer(paramAnonymousDialogInterface);
				pingjiao_First();
			}
		});
		localBuilder.show();
	}

	/**
	 * 评教第三步
	 */
	private void pingjiao_Third() {
		View localView = this.inflater.inflate(R.layout.pingjiao_third, null);
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(instance);
		ProgressBar progressBar = (ProgressBar) localView.findViewById(R.id.progressBar_pingjiaoOperate);
		TextView TVpingjiao = (TextView) localView.findViewById(R.id.pingjiao_Course_Score);
		TVpingjiao.setText(pingjiao_Course_Score + "分");
		progressBar.setProgress(pingjiao_Course_Score);
		localBuilder.setView(localView);
		localBuilder.setTitle("评教第三步");
		localBuilder.setPositiveButton("确认评教", new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(DialogInterface dialog, int which) {
				toast("正在评教中。。。。");
				new Thread() {
					int i = 0;

					public void run() {
						while (EVENTVALIDATION.isEmpty() || VIEWSTATE.isEmpty()) {
							try {
								Thread.sleep(1000);
								i++;
								if (i > 4)
									break;
							} catch (InterruptedException e) {
							}
						}
						if (i > 4) {
							Message msg = new Message();
							msg.obj = new String("评教失败，网络不稳定");
							msg.what = 4;
							bh.sendMessage(msg);
						}
						operatePost();

					}

					private void operatePost() {
						HashMap<String, String> taskArgs = new HashMap<String, String>();
						taskArgs.put("__EVENTVALIDATION", EVENTVALIDATION);
						taskArgs.put("__VIEWSTATE", VIEWSTATE);
						taskArgs.put("GridCourse2$ctl02$userscore", String.valueOf(pingjiao_Course_Score));
						taskArgs.put("GridCourse2$ctl03$userscore", String.valueOf(pingjiao_Course_Score));
						taskArgs.put("GridCourse2$ctl04$userscore", String.valueOf(pingjiao_Course_Score));
						taskArgs.put("GridCourse2$ctl05$userscore", String.valueOf(pingjiao_Course_Score));
						taskArgs.put("GridCourse2$ctl06$userscore", String.valueOf(pingjiao_Course_Score));
						taskArgs.put("GridCourse2$ctl07$userscore", String.valueOf(pingjiao_Course_Score));
						taskArgs.put("GridCourse2$ctl08$userscore", String.valueOf(pingjiao_Course_Score));
						taskArgs.put("GridCourse2$ctl09$userscore", String.valueOf(pingjiao_Course_Score));
						taskArgs.put("SuitTeach", checkBoxString);
						taskArgs.put("TeacherChange", teacherChangeString);
						taskArgs.put("TeacherGood", teacherGoodString);
						taskArgs.put("btnSave", "·确定·");
						taskPool.addTask(C.task.postCourse, "Stu_Assess/Stu_Assess_Proc.aspx?id=" + pingjiao_Operate_Id, taskArgs, new BaseTask() {
							public void onComplete(InputStream paramAnonymousInputStream) {
								post();
								sendMessage(BaseTask.TASK_COMPLETE, C.task.postCourse, "1");
							}

							public void onError(String paramAnonymousString) {
								sendMessage(BaseTask.NETWORK_ERROR, C.task.postCourse, null);
							}
						}, 0);
					}
				}.start();
			}
		});
		localBuilder.show();

	}

	/**
	 * 上传评教结果到自己的服务器
	 */
	public void post() {
		RequestParams params = new RequestParams();
		params.put("course", course.getCourse());
		params.put("teacher", course.getTeacher());
		params.put("usernumber", user.getUserNumber());
		params.put("type", "1");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("pingjiao_Course_Score", String.valueOf(pingjiao_Course_Score));
		map.put("SuitTeach", checkBoxString);
		map.put("TeacherChange", teacherChangeString);
		map.put("TeacherGood", teacherGoodString);
		
		params.put("score", Json.mapTojson(map));

		AsyncHttp.Post(C.api.uploadscore, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try {
					String reaponse = new String(responseBody, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			}
		});
	}
}
