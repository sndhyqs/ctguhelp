package com.example.login;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONArray;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.ctgu.adapter.TransactionsAdapter;
import com.ctgu.base.BaseActivity;
import com.ctgu.base.BaseTask;
import com.ctgu.base.C;
import com.ctgu.ctguhelp.R;
import com.ctgu.model.TransactionsGoods;
import com.ctgu.pulldownlistview.LoadingHelper;
import com.ctgu.pulldownlistview.LoadingListener;
import com.ctgu.pulldownlistview.PullDownRefreshView;
import com.ctgu.pulldownlistview.PullDownRefreshView.onLoadMoreListener;
import com.ctgu.pulldownlistview.PullDownRefreshView.pullToRefreshListener;
import com.ctgu.util.AppCache;
import com.ctgu.util.AsyncHttp;
import com.ctgu.util.XmlToString;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Transactions extends BaseActivity implements LoadingListener {
	public int DataSizePerPage = 10;
	TransactionsAdapter adapter;
	ListView listView;
	LoadingHelper loadingHelper;
	ArrayList<TransactionsGoods> message;
	public int pageindex = 1;
	PullDownRefreshView refreshView;
	ImageButton addbutton;
	Button messgae_good;
	private Context context;
	private HashMap<String, ArrayList<Bitmap>> map;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tranion);

		context = getApplicationContext();
		refreshView = (PullDownRefreshView) findViewById(R.id.pulldown_refreshview);
		addbutton = (ImageButton) findViewById(R.id.add_button);
		listView = (ListView) findViewById(R.id.pulldown_listview);
		adapter = new TransactionsAdapter(this, Transactions.this);
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
		
		addbutton.setOnClickListener(new Onclik());
		doLoadMore();

	}

	public void doLoadMore() {
		RequestParams params = new RequestParams();
		params.put("user", user.getMy_userName());
		params.put("pageindex", String.valueOf(pageindex));
		AsyncHttp.Post(C.api.getTranion, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try {
					String reaponse = new String(responseBody, "utf-8");
					InputStream is = XmlToString.getStringStream(reaponse);
					ArrayList<TransactionsGoods> t = XmlToString.xml_to_tranion(is);
					message = t;
					Success();
					getImage(t);
				} catch (UnsupportedEncodingException e) {
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			}
		});

	}

	public void onTaskComplete(int taskId, Object message) {
		switch (taskId) {
		case C.task.getImage:
			map = (HashMap<String, ArrayList<Bitmap>>) message;
			adapter.setMap(map);
			break;
		case C.task.getOneMessage:

			break;
		}
	}

	public void Success() {
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
				Success();
			}
		}, 0);
	}

	public void getImage(final ArrayList<TransactionsGoods> t) {
		doTaskAsync(C.task.getImage, new BaseTask() {

			@Override
			public void onComplete() {
				HashMap<String, ArrayList<Bitmap>> map = new HashMap<String, ArrayList<Bitmap>>();
				for (int i = 0; i < t.size(); i++) {
					String url = t.get(i).getPicUrl();
					JSONArray jsonA;
					try {
						jsonA = new JSONArray(url);
						if (jsonA.length() > 0) {
							ArrayList<Bitmap> ab = new ArrayList<Bitmap>();
							for (int j = 0; j < jsonA.length(); j++) {
								String myurl = jsonA.getString(j);
								ab.add(AppCache.getCachedImage(context, myurl));
							}
							map.put(String.valueOf(t.get(i).getId()), ab);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				sendMessage(BaseTask.TASK_COMPLETE, getId(), map);
			}

		}, 0);
	}

	class Onclik implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.add_button:
				forward_not_finish(TranionRightDialog.class);
				break;

			}
		}
	}

	public void onItem(int id, TransactionsGoods item) {
		Bundle b = new Bundle();
		switch (id) {
		case R.id.conview:

			b.putString("name", item.getName());
			b.putString("time", item.getTime());
			b.putString("tel", item.getTel());
			b.putString("picurl", item.getPicUrl());
			b.putInt("id", item.getId());
			b.putString("describe", item.getDescribe());
			b.putString("read", item.getRead());
			b.putString("discuss", item.getDiscuss());
			forward_not_finish(TranionConView.class, b);

			break;
		case R.id.messgae_discuss:
			b.putInt("id", item.getId());
			b.putString("user", item.getName());
			forward_not_finish(TranionDiscuss.class, b);
			break;

		}

	}

	public void gridItem(String url, int position) {
		JSONArray jsonA;
		try {
			jsonA = new JSONArray(url);
			if (jsonA.length() > 0) {

				String myurl = jsonA.getString(position);
				Bundle params = new Bundle();
				params.putString("path", myurl);
				forward_not_finish(LoadImage.class, params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
