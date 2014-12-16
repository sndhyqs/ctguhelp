package com.example.login;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.ctgu.adapter.WaiMaiAdapter;
import com.ctgu.base.BaseActivity;
import com.ctgu.base.BaseHandler;
import com.ctgu.base.BaseTask;
import com.ctgu.base.BaseUiAuth;
import com.ctgu.base.C;
import com.ctgu.ctguhelp.R;
import com.ctgu.model.WaiMaiM;
import com.ctgu.util.AppCache;

public class WaiMai extends BaseUiAuth {
	private Context conext;
	private WaiMaiAdapter adapter;
	private BaseHandler bh;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		conext = getApplicationContext();
		setContentView(R.layout.waimai);
		adapter = new WaiMaiAdapter(conext);
		ListView list = (ListView) findViewById(R.id.list_waimai);
		ArrayList<WaiMaiM> data = new ArrayList<WaiMaiM>();
		for (int i = 0; i < 10; i++) {
			data.add(new WaiMaiM(i + "商家名称", "3", i + "", C.dir.qq + (i + 1) % 3 + ".jpg", i + ""));
		}
		final ArrayList<WaiMaiM> datas = new ArrayList<WaiMaiM>(data);
		doTaskAsync(5, new BaseTask() {

			@Override
			public void onComplete() {
				ArrayList<Bitmap> ab = new ArrayList<Bitmap>();
				for (int i = 0; i < 10; i++) {
					String url = datas.get(i).getTubiao();
					AppCache.getCachedImage(conext, url);
				}
				sendMessage(BaseTask.TASK_COMPLETE, getId(), null);
			}

		}, 0);

		list.setAdapter(adapter);
		adapter.addItems(data);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				WaiMaiM w = adapter.getItem(position);
				Bundle b = new Bundle();
				b.putSerializable("title", w.getName());
				forward_not_finish(WMGoods.class, b);
			}
		});

	}

	@Override
	public void onTaskComplete(int taskid) {

		switch (taskid) {
		case 5:
			adapter.mynotify();
			break;
		}
	}
}
