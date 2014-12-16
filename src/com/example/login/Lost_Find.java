package com.example.login;

import android.view.View;
import android.view.View.OnClickListener;
import android.app.ProgressDialog;

import com.ctgu.model.Lost_Find_Message;
import com.ctgu.util.IO;
import com.ctgu.util.NewTaskPool;
import com.ctgu.util.XmlToString;
import com.ctgu.adapter.Lost_Find_Adapter;
import com.ctgu.base.BaseTask;
import com.ctgu.base.C;

import android.widget.AdapterView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.ctgu.base.BaseUiAuth;
import com.ctgu.ctguhelp.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.ListAdapter;

public class Lost_Find extends BaseUiAuth {
	AlertDialog.Builder builder;
	private LayoutInflater inflater;
	ListView list_lost_find;
	private int list_show_now;
	ArrayList<Lost_Find_Message> lost_find_message;
	private TextView lost_find_name_view, lost_find_title_view,
			lost_find_describe_view, lost_find_time_view;
	private TextView lost_find_other_view;
	private TextView lost_find_phone_view;
	private TextView lost_find_qq_view;
	ArrayList<Lost_Find_Message> lost_lost_message;
	private Lost_Find_Message message;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lost_find);
		inflater = LayoutInflater.from(this);

		list_lost_find = (ListView) findViewById(R.id.list_lost_find);
		Button get_lost_goods_button = (Button) findViewById(R.id.get_lost_goods_button);
		Button get_find_goods_button = (Button) findViewById(R.id.get_find_goods_button);
		ImageButton lost_find_edit_button = (ImageButton) findViewById(R.id.lost_find_edit_button);

		get_lost_goods_button.setOnClickListener(new On_Click());
		get_find_goods_button.setOnClickListener(new On_Click());
		lost_find_edit_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				forward_not_finish(Lost_Find_Edit.class);
			}
		});
		list_lost_find.setOnItemClickListener(new Lost_Item_On_Click());
	}

	public void onTaskComplete(int taskId, Object message) {
		switch (taskId) {
		case C.task.getLostGoods:
			if (pd.isShowing()) {
				pd.dismiss();
			}
			lost_lost_message = (ArrayList<Lost_Find_Message>) message;
			list_lost_find.setAdapter(new Lost_Find_Adapter(this,
					lost_lost_message));
			list_show_now = 1;
			break;
		case C.task.getFindGoods:
			if (pd.isShowing()) {
				pd.dismiss();
			}
			lost_find_message = (ArrayList<Lost_Find_Message>) message;
			list_lost_find.setAdapter(new Lost_Find_Adapter(this,
					lost_find_message));
			list_show_now = 0;
			break;
		case C.task.getOneGoods:
			String s = (String) message;
			String[] data = s.split("&");
			builder = new AlertDialog.Builder(this);

			View vss = inflater.inflate(R.layout.lost_find_view_dialog, null);
			builder.setTitle("内容详情").setView(vss);
			lost_find_title_view = (TextView) vss
					.findViewById(R.id.lost_find_title_view);
			lost_find_describe_view = (TextView) vss
					.findViewById(R.id.lost_find_describe_view);
			lost_find_time_view = (TextView) vss
					.findViewById(R.id.lost_find_time_view);
			lost_find_name_view = (TextView) vss
					.findViewById(R.id.lost_find_name_view);
			lost_find_phone_view = (TextView) vss
					.findViewById(R.id.lost_find_phone_view);
			lost_find_qq_view = (TextView) vss
					.findViewById(R.id.lost_find_qq_view);
			lost_find_other_view = (TextView) vss
					.findViewById(R.id.lost_find_other_view);
			lost_find_name_view.setText(data[0]);
			lost_find_phone_view.setText(data[1]);
			lost_find_qq_view.setText(data[2]);
			lost_find_title_view.setText(this.message.getTitle());
			lost_find_describe_view.setText(this.message.getKeyword());
			lost_find_time_view.setText(this.message.getTime());
			lost_find_other_view.setText(data[3]);
			builder.show();
			break;

		}
	}

	class Lost_Item_On_Click implements AdapterView.OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (list_show_now == 1) {
				message = lost_lost_message.get(position);
			} else {
				message = lost_find_message.get(position);
			}

			task_pool_get_lost_find.addTask(C.task.getOneGoods,
					C.api.getOneGoods + "?id=" + message.getId(),
					new BaseTask() {
						public void onComplete(InputStream inputStream) {
							String data = IO.inputSreamToString(inputStream);
							sendMessage(BaseTask.TASK_COMPLETE, getId(), data);
						}

						public void onError(String error) {
							sendMessage(BaseTask.NETWORK_ERROR, getId(), null);
						}
					}, 0);
		}
	}

	class On_Click implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.get_lost_goods_button:
				pd.show();
				task_pool_get_lost_find.addTask(C.task.getLostGoods,
						C.api.getLostGoods, new BaseTask() {
							public void onComplete(InputStream inputStream) {
								ArrayList<Lost_Find_Message> lost_message = XmlToString
										.get_lost_message(inputStream);
								sendMessage(BaseTask.TASK_COMPLETE, getId(),
										lost_message);
							}

							public void onError(String error) {
								sendMessage(BaseTask.NETWORK_ERROR, getId(),
										null);
							}
						}, 0);
				break;
			case R.id.get_find_goods_button:
				pd.show();
				task_pool_get_lost_find.addTask(C.task.getFindGoods,
						C.api.getFindGoods, new BaseTask() {
							public void onComplete(InputStream inputStream) {
								ArrayList<Lost_Find_Message> lost_find_message = XmlToString
										.get_lost_message(inputStream);
								sendMessage(BaseTask.TASK_COMPLETE, getId(),
										lost_find_message);
							}

							public void onError(String error) {
								sendMessage(BaseTask.NETWORK_ERROR, getId(),
										null);
							}
						}, 0);
				break;

			}
		}
	}
}
