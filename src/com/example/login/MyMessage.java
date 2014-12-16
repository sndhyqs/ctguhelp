package com.example.login;

import android.view.View;
import android.view.View.OnClickListener;

import com.ctgu.adapter.MessageBoardAdapter;
import com.ctgu.adapter.MyMessageAdapter;
import com.ctgu.adapter.ReviewAdapter;
import com.ctgu.base.BaseUiAuth;
import com.ctgu.base.C;
import com.ctgu.model.Message;
import com.ctgu.model.MyMessages;
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

import org.apache.http.Header;

import android.os.Handler;
import com.ctgu.base.BaseTask;
import com.ctgu.ctguhelp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MyMessage extends BaseUiAuth implements LoadingListener {
	public int DataSizePerPage = 20;
	MyMessageAdapter adapter;
	ListView listView;
	LoadingHelper loadingHelper;
	ArrayList<MyMessages> message;
	public int pageindex = 1;
	PullDownRefreshView refreshView;
	ImageButton message_edit_button;
	Button messgae_good;
	private int position;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_message);

		refreshView = (PullDownRefreshView) findViewById(R.id.pulldown_refreshview);

		listView = (ListView) findViewById(R.id.pulldown_listview);
		adapter = new MyMessageAdapter(this, this);
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

		doLoadMore();
		delect();
	}

	private void delect() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("user", user.getMy_userName());
		params.put("type", String.valueOf("0"));
		client.post(C.api.baseCtguHelp + C.api.delectRemind, params, new AsyncHttpResponseHandler() {

			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

			}

			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				System.out.println(statusCode);

			}
		});

	}

	public void doLoadMore() {
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("user", user.getMy_userName());
		taskArgs.put("pageindex", String.valueOf(pageindex));
		taskPool_ctguHelp.addTask(C.task.getMyMessgae, C.api.getMyMessgae, taskArgs, new BaseTask() {

			@Override
			public void onComplete(InputStream inputStream) {

				ArrayList<MyMessages> result = XmlToString.xml_to_myMessage(inputStream);

				sendMessage(BaseTask.TASK_COMPLETE, C.task.getMyMessgae, result);
			}

			@Override
			public void onError(String error) {
				sendMessage(BaseTask.NETWORK_ERROR, C.task.getMyMessgae, null);
			}

		}, 0);
	}

	public void onTaskComplete(int taskId, Object message) {
		switch (taskId) {
		case C.task.getMyMessgae:
			this.message = (ArrayList<MyMessages>) message;
			onSuccess();
			break;
		case C.task.getOneMessage:
			Bundle c = new Bundle();
			ArrayList<Message> m = (ArrayList<Message>) message;
			c.putInt("id", m.get(0).getId());
			c.putInt("good", m.get(0).getGood());
			c.putInt("read", m.get(0).getRead());
			c.putString("content", m.get(0).getContent());
			c.putString("time", m.get(0).getTime());
			c.putString("user", m.get(0).getUser());
			forward(ConView.class, c);

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
		adapter.addItems(message);
		if ((message == null || message.size() == 0) && pageindex == 1) {
			loadingHelper.ShowEmptyData();
			refreshView.removeListFootView();
			return;
		}
		if ((message == null) || (message.size() < DataSizePerPage)) {
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
		MyMessages m = (MyMessages) object;
		switch (v) {
		case R.id.review:
			Bundle b = new Bundle();
			b.putString("id", m.getId());
			b.putString("contentId", m.getContent_id());
			forward_not_finish(ReviewEdit.class, b);
			break;
		case R.id.conview:
			HashMap<String, String> taskArgs = new HashMap<String, String>();
			taskArgs.put("id", m.getContent_id());
			taskPool_ctguHelp.addTask(C.task.getOneMessage, C.api.getOneMessage, taskArgs, new BaseTask() {

				@Override
				public void onComplete(InputStream inputStream) {
					ArrayList<Message> result = XmlToString.get_message_board(inputStream);

					sendMessage(BaseTask.TASK_COMPLETE, C.task.getOneMessage, result);
				}

				@Override
				public void onError(String error) {
					sendMessage(BaseTask.NETWORK_ERROR, C.task.getOneMessage, null);
				}

			}, 0);

			break;
		}
	}
}
