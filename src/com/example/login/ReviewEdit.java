package com.example.login;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.ctgu.base.BaseTask;
import com.ctgu.base.BaseUiAuth;
import com.ctgu.base.C;
import com.ctgu.ctguhelp.R;
import com.ctgu.util.IO;
import com.example.login.TranionDiscuss.on_Click;

public class ReviewEdit extends BaseUiAuth {
	EditText et_discuss;
	private String id;
	private String contentId;
	private ImageView expression;
	private Builder builder;
	private int[] imageIds = new int[107];

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discuss);

		expression = (ImageView) findViewById(R.id.team_singlechat_id_expression);
		et_discuss = (EditText) findViewById(R.id.et_discuss);
		Button bt_discuss = (Button) findViewById(R.id.bt_discuss);
		et_discuss.setOnClickListener(new onClick());
		bt_discuss.setOnClickListener(new onClick());
		expression.setOnClickListener(new onClick());
		Bundle b = getIntent().getExtras();
		this.id = b.getString("id");
		this.contentId = b.getString("contentId");
	}

	@Override
	public void onTaskComplete(int taskId, Object message) {
		switch (taskId) {
		case C.task.setReview:
			if (pd.isShowing()) {
				pd.dismiss();
			}
			toast((String) message);
			finish();

		}
	}

	class onClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bt_discuss:
				String content = et_discuss.getText().toString().trim();
				HashMap<String, String> taskArgs = new HashMap<String, String>();
				taskArgs.put("id", id);
				taskArgs.put("content", content);
				taskArgs.put("user", user.getMy_userName());
				taskArgs.put("user_review", "1");
				taskArgs.put("contentId", contentId);
				pd.show();
				taskPool_ctguHelp.addTask(C.task.setReview, C.api.setReview, taskArgs, new BaseTask() {

					@Override
					public void onComplete(InputStream inputStream) {
						String data = IO.inputSreamToString(inputStream);
						sendMessage(BaseTask.TASK_COMPLETE, C.task.setReview, data);
					}

					@Override
					public void onError(String error) {
						sendMessage(BaseTask.NETWORK_ERROR, C.task.setReview, null);
					}

				}, 0);
				break;
			case R.id.team_singlechat_id_expression:
				createExpressionDialog();
				break;
			}

		}

	}
	private void createExpressionDialog() {
		builder = new AlertDialog.Builder(ReviewEdit.this);
		GridView gridView = createGridView();
		builder.setView(gridView);
		final AlertDialog bb = builder.show();
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				int resId = imageIds[arg2 % imageIds.length];
				ImageSpan imageSpan = new ImageSpan(ReviewEdit.this, resId, 10);
				String str = null;
				if (arg2 < 10) {
					str = "f00" + arg2;
				} else if (arg2 < 100) {
					str = "f0" + arg2;
				} else {
					str = "f" + arg2;
				}
				SpannableString spannableString = new SpannableString(str);
				spannableString.setSpan(imageSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				et_discuss.append(spannableString);
				bb.dismiss();
			}
		});
	}

	private GridView createGridView() {
		final GridView view = new GridView(this);
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		// 生成107个表情的id，封装
		for (int i = 0; i < 107; i++) {
			try {
				if (i < 10) {
					Field field = R.drawable.class.getDeclaredField("f00" + i);
					int resourceId = Integer.parseInt(field.get(null).toString());
					imageIds[i] = resourceId;
				} else if (i < 100) {
					Field field = R.drawable.class.getDeclaredField("f0" + i);
					int resourceId = Integer.parseInt(field.get(null).toString());
					imageIds[i] = resourceId;
				} else {
					Field field = R.drawable.class.getDeclaredField("f" + i);
					int resourceId = Integer.parseInt(field.get(null).toString());
					imageIds[i] = resourceId;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("image", imageIds[i]);
			listItems.add(listItem);
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.team_layout_single_expression_cell, new String[] { "image" }, new int[] { R.id.image });
		view.setAdapter(simpleAdapter);
		view.setNumColumns(6);
		view.setBackgroundColor(Color.rgb(214, 211, 214));
		view.setHorizontalSpacing(1);
		view.setVerticalSpacing(1);
		view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		view.setGravity(Gravity.CENTER);
		return view;
	}
}
