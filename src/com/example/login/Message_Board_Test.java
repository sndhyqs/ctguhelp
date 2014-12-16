package com.example.login;

import android.view.View;
import android.view.View.OnClickListener;

import com.ctgu.adapter.MessageBoardAdapter;
import com.ctgu.base.BaseUiAuth;
import com.ctgu.base.C;
import com.ctgu.model.Message;
import com.ctgu.pulldownlistview.LoadingListener;
import com.ctgu.pulldownlistview.PullDownRefreshView.onLoadMoreListener;
import com.ctgu.pulldownlistview.PullDownRefreshView.pullToRefreshListener;
import android.widget.ListView;
import com.ctgu.pulldownlistview.LoadingHelper;

import java.io.InputStream;
import java.util.ArrayList;
import com.ctgu.pulldownlistview.PullDownRefreshView;
import com.ctgu.util.XmlToString;

import java.util.HashMap;
import android.os.Handler;
import com.ctgu.base.BaseTask;
import com.ctgu.ctguhelp.R;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Message_Board_Test extends BaseUiAuth implements LoadingListener {
	public int DataSizePerPage = 20;
	MessageBoardAdapter adapter;
	ListView listView;
	LoadingHelper loadingHelper;
	ArrayList<Message> messgae_board;
	public int pageindex = 1;
	PullDownRefreshView refreshView;
	ImageButton message_edit_button;
	Button messgae_good;
	private int position;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_board);

		message_edit_button = (ImageButton) findViewById(R.id.message_board_edit_button);
		refreshView = (PullDownRefreshView) findViewById(R.id.pulldown_refreshview);

		listView = (ListView) findViewById(R.id.pulldown_listview);
		adapter = new MessageBoardAdapter(this, this);
		listView.setAdapter(adapter);
		loadingHelper = new LoadingHelper(this, findViewById(R.id.loading_prompt_linear), findViewById(R.id.loading_empty_prompt_linear));

		loadingHelper.ShowLoading();
		loadingHelper.SetListener(this);

		refreshView.setOnRefreshListener(new pullToRefreshListener() {
			public void onRefresh() {
				refreshView.post(new Runnable() {
					public void run() {
						refreshView.setOnLoadState(false, true);
						pageindex = 1;
						doLoadMore();
					}
				});
			}
		}, 0);
		refreshView.setOnLoadMoreListener(new onLoadMoreListener() {
			public void onLoadMore() {
				refreshView.setOnLoadState(false, false);
				pageindex = (pageindex + 1);
				doLoadMore();
			}
		});

		message_edit_button.setOnClickListener(new Onclik());
		doLoadMore();

	}

	public void doLoadMore() {
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("pageindex", String.valueOf(pageindex));
		taskPool_ctguHelp.addTask(C.task.getMessageBoard, C.api.getMessageBoard, taskArgs, new BaseTask() {

			@Override
			public void onComplete(InputStream inputStream) {
				ArrayList<Message> result = XmlToString.get_message_board(inputStream);

				sendMessage(BaseTask.TASK_COMPLETE, C.task.getMessageBoard, result);
			}

			@Override
			public void onError(String error) {
				sendMessage(BaseTask.NETWORK_ERROR, C.task.getMessageBoard, null);
			}

		}, 0);
	}

	public void onTaskComplete(int taskId, Object message) {
		switch (taskId) {
		case C.task.getMessageBoard:
			messgae_board = (ArrayList<Message>) message;
			onSuccess();
			break;
		case C.task.set_good:
			adapter.setGood(position);
			toast((String) message);
			break;

		}
	}

	public void onSuccess() {
		loadingHelper.HideLoading(8);

		if (refreshView.getRefreshState()) {
			if (adapter != null) {
				adapter.clear();
			}
			refreshView.finishRefreshing();
		}
		refreshView.setOnLoadState(false, false);
		refreshView.initListFootView(adapter);
		adapter.addItems(messgae_board);
		if ((messgae_board == null || messgae_board.size() == 0) && pageindex == 1) {
			loadingHelper.ShowEmptyData();
			refreshView.removeListFootView();
			return;
		}
		if ((messgae_board == null) || (messgae_board.size() < DataSizePerPage)) {
			Toast.makeText(this, R.string.loading_data_finished, Toast.LENGTH_SHORT).show();
			refreshView.removeListFootView();
		}
	}

	public void onFail() {
		loadingHelper.ShowError("显示网络出错信息!!");
	}

	public void OnRetryClick() {
		loadingHelper.ShowLoading();
		if (adapter != null)
			adapter.clear();
		refreshView.setOnLoadState(false, false);
		pageindex = 1;
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				loadingHelper.HideLoading(8);
				onSuccess();
			}
		}, 0);
	}

	class Onclik implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.message_board_edit_button:
				forward_not_finish(MainTopRightDialog.class);
				break;

			}
		}
	}

	public void onItem(int v, int p, Object object) {
		this.position = p;
		Message m = (Message) object;
		switch (v) {
		case R.id.messgae_good:
			HashMap<String, String> taskArgs = new HashMap<String, String>();
			taskArgs.put("id", String.valueOf(m.getId()));
			taskArgs.put("user", user.getMy_userName());
			taskPool_ctguHelp.addTask(C.task.set_good, C.api.set_good, taskArgs, new BaseTask() {

				@Override
				public void onComplete(InputStream inputStream) {
					String data = "操作成功";
					sendMessage(BaseTask.TASK_COMPLETE, C.task.set_good, data);
				}

				@Override
				public void onError(String error) {
					sendMessage(BaseTask.NETWORK_ERROR, C.task.set_good, null);
				}

			}, 0);

			break;

		case R.id.messgae_discuss:
			Bundle b = new Bundle();
			b.putInt("key", m.getId());
			forward(Discuss.class, b);
			break;
		case R.id.conview:
			Bundle c = new Bundle();
			Message m1 = (Message) object;
			c.putInt("id", m.getId());
			c.putInt("good", m.getGood());
			c.putInt("read", m.getRead());
			c.putString("content", m.getContent());
			c.putString("time", m.getTime());
			c.putString("user", m.getUser());
			c.putInt("discuss", m.getDiscuss());
			forward(ConView.class, c);
			break;
		}

	}
}
