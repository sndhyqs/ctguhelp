package com.example.login;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ctgu.adapter.WaiMaiGoodsAdapter;
import com.ctgu.base.BaseActivity;
import com.ctgu.ctguhelp.R;
import com.ctgu.model.WaiMaiGoods;

/**
 * 外卖商家商品展示页面
 * 
 * @author Administrator
 * 
 */
public class WMGoods extends BaseActivity {
	private Context conext;
	private WaiMaiGoodsAdapter adapter;
	private ArrayList<WaiMaiGoods> adapterData;
	private ArrayList<WaiMaiGoods> data;
	private ListView list;
	private LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wmgoods);
		inflater = LayoutInflater.from(this);
		conext = getApplicationContext();
		list = (ListView) findViewById(R.id.list);
		TextView tvtitle = (TextView) findViewById(R.id.tvtitle);
		Button bt1 = (Button) findViewById(R.id.bt1);
		Button bt2 = (Button) findViewById(R.id.bt2);
		Button bt3 = (Button) findViewById(R.id.bt3);
		Bundle b = getIntent().getExtras();
		tvtitle.setText(b.getString("title"));
		adapter = new WaiMaiGoodsAdapter(conext);
		list.setAdapter(adapter);
		data = new ArrayList<WaiMaiGoods>();
		adapterData = new ArrayList<WaiMaiGoods>();
		for (int i = 0; i < 20; i++) {
			data.add(new WaiMaiGoods("price", "picUrl", "tradeNumber", i, i % 3, "商品" + i));
		}
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				toast(adapterData.get(position).toString());
				AlertDialog.Builder builder = new AlertDialog.Builder(WMGoods.this);
				builder.setTitle("购买数量");
				View vss = inflater.inflate(R.layout.waimaidnumber, null);
				final TextView number = (TextView) vss.findViewById(R.id.number);
				builder.setView(vss);
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						int j;
						String n = number.getText().toString();
						try {
							 j = Integer.parseInt(n);

						} catch (Exception localException) {
							toast("输入错误");
							j = 0;
						}
						System.out.println(j);
					}
				});
				builder.setNegativeButton("取消", null);
				builder.show();
			}

		});
		bt1.setOnClickListener(new Click(0));
		bt2.setOnClickListener(new Click(1));
		bt3.setOnClickListener(new Click(2));

	}

	private class Click implements OnClickListener {
		private int type;

		public Click(int type) {
			super();
			this.type = type;
		}

		@Override
		public void onClick(View v) {
			adapterData.clear();
			for (int i = 0; i < 20; i++) {
				WaiMaiGoods d = data.get(i);
				if (d.getTypeId() == type) {
					adapterData.add(d);
				}
				adapter.clear();
				adapter.addItems(adapterData);
			}
		}
	}
}
