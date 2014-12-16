package com.example.login;

import android.view.View;
import android.view.View.OnClickListener;

import com.ctgu.adapter.MessageBoardAdapter;
import com.ctgu.adapter.MyMessageAdapter;
import com.ctgu.adapter.ReviewAdapter;
import com.ctgu.adapter.TranionMyMessageAdapter;
import com.ctgu.base.BaseUiAuth;
import com.ctgu.base.C;
import com.ctgu.model.Message;
import com.ctgu.model.MyMessages;
import com.ctgu.model.TransactionsGoods;
import com.ctgu.pulldownlistview.LoadingListener;
import com.ctgu.pulldownlistview.PullDownRefreshView.onLoadMoreListener;
import com.ctgu.pulldownlistview.PullDownRefreshView.pullToRefreshListener;
import android.widget.ListView;
import com.ctgu.pulldownlistview.LoadingHelper;

import java.io.InputStream;
import java.util.ArrayList;
import com.ctgu.pulldownlistview.PullDownRefreshView;
import com.ctgu.util.IO;
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
import android.widget.TextView;
import android.widget.Toast;

public class TranionMyMessage extends BaseUiAuth implements LoadingListener {
	public int DataSizePerPage = 20;
	TranionMyMessageAdapter adapter;
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
		TextView mytitle =(TextView) findViewById(R.id.mytitle);
		mytitle.setText("我的消息（二手交易）");
		listView = (ListView) findViewById(R.id.pulldown_listview);
		adapter = new TranionMyMessageAdapter(this, this);
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
		params.put("type", String.valueOf("1"));
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
		taskPool_ctguHelp.addTask(C.task.getMyMessgae, C.api.getTranionMyMessgae, taskArgs, new BaseTask() {

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
			Bundle b = new Bundle();
			ArrayList<TransactionsGoods> t = (ArrayList<TransactionsGoods>) message;
			TransactionsGoods item = t.get(0);
			b.putString("name", item.getName());
			b.putString("time", item.getTime());
			b.putString("tel", item.getTel());
			b.putString("picurl", item.getPicUrl());
			b.putInt("id", item.getId());
			b.putString("describe", item.getDescribe());
			b.putString("read", item.getRead());
			b.putString("discuss", item.getDiscuss());
			forward(TranionConView.class, b);

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
			forward_not_finish(TranionReviewEdit.class, b);
			break;
		case R.id.conview:
			HashMap<String, String> taskArgs = new HashMap<String, String>();
			taskArgs.put("id", m.getContent_id());
			taskPool_ctguHelp.addTask(C.task.getOneMessage, C.api.getTranionOneMessage, taskArgs, new BaseTask() {

				@Override
				public void onComplete(InputStream inputStream) {
					ArrayList<TransactionsGoods> t = XmlToString.xml_to_tranion(inputStream);

					sendMessage(BaseTask.TASK_COMPLETE, C.task.getOneMessage, t);
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
