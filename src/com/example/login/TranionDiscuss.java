package com.example.login;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.ctgu.base.BaseTask;
import com.ctgu.base.BaseUiAuth;
import com.ctgu.base.C;
import com.ctgu.ctguhelp.R;
import com.ctgu.model.TransactionsGoods;
import com.ctgu.util.AsyncHttp;
import com.ctgu.util.IO;
import com.ctgu.util.XmlToString;
import com.example.login.Message_Board_Edit.On_click;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TranionDiscuss extends BaseUiAuth {
	EditText et_discuss;
	private int id;
	private int[] imageIds = new int[107];
	private Builder builder;
	private ImageView expression;
	private String conutentuser;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discuss);

		et_discuss = (EditText) findViewById(R.id.et_discuss);
		Button bt_discuss = (Button) findViewById(R.id.bt_discuss);
		et_discuss.setOnClickListener(new on_Click());
		bt_discuss.setOnClickListener(new on_Click());
		Bundle b = getIntent().getExtras();
		this.id = b.getInt("id");
		conutentuser = b.getString("user");
		expression = (ImageView) findViewById(R.id.team_singlechat_id_expression);
		expression.setOnClickListener(new on_Click());
	}

	@Override
	public void onTaskComplete(int taskId, Object message) {
		switch (taskId) {
		case C.task.setDiscuss:
			if (pd.isShowing()) {
				pd.dismiss();
			}
			toast((String) message);
			finish();

		}
	}

	class on_Click implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bt_discuss:
				String content = et_discuss.getText().toString().trim();
				pd.show();
				RequestParams params = new RequestParams();
				params.put("content_user", conutentuser);
				params.put("content", content);
				params.put("content_id", String.valueOf(id));
				params.put("user", user.getMy_userName());
				AsyncHttp.Post(C.api.addTranionDisscuss, params, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
						try {
							if (pd.isShowing()) {
								pd.dismiss();
							}
							String reaponse = new String(responseBody, "utf-8");
							toast(reaponse);
							finish();
						} catch (UnsupportedEncodingException e) {
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
					}
				});
				break;
			case R.id.team_singlechat_id_expression:
				createExpressionDialog();
				break;
			}

		}

	}

	private void createExpressionDialog() {
		builder = new AlertDialog.Builder(TranionDiscuss.this);
		GridView gridView = createGridView();
		builder.setView(gridView);
		final AlertDialog bb = builder.show();
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				int resId = imageIds[arg2 % imageIds.length];
				ImageSpan imageSpan = new ImageSpan(TranionDiscuss.this, resId, 10);
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
