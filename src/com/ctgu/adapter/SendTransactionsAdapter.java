/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package com.ctgu.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ctgu.adapter.MessageBoardAdapter.onClick;
import com.ctgu.ctguhelp.R;
import com.ctgu.model.TransactionsGoods;
import com.ctgu.util.ExpressionUtil;
import com.example.login.SendTransactions;
import com.example.login.Transactions;

public class SendTransactionsAdapter extends BaseAdapter {
	private int index;
	private Context context;
	private ArrayList<TransactionsGoods> messageArr;
	private HashMap<String, ArrayList<Bitmap>> map;
	private SendTransactions activity;

	public SendTransactionsAdapter(Context conext, SendTransactions activity) {
		messageArr = new ArrayList<TransactionsGoods>();
		this.context = conext;
		map = new HashMap<String, ArrayList<Bitmap>>();
		this.activity = activity;
	}

	public void addItems(ArrayList<TransactionsGoods> newItems) {
		if ((newItems == null) || (newItems.size() <= 0)) {
			return;
		}
		for (int i = 0; i < newItems.size(); i++) {
			messageArr.add(newItems.get(i));
		}

		notifyDataSetChanged();
	}

	public void clear() {
		messageArr.clear();
		notifyDataSetChanged();
	}

	public int getCount() {
		return messageArr.size();
	}

	public TransactionsGoods getItem(int position) {
		return messageArr.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public void setMap(HashMap<String, ArrayList<Bitmap>> map) {
		this.map = map;
		notifyDataSetChanged();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TransactionsGoods goods = messageArr.get(position);
		this.index = position;
		View v = View.inflate(context, R.layout.tranion_item, null);
		TextView title = (TextView) v.findViewById(R.id.goods_title);
		TextView describe = (TextView) v.findViewById(R.id.goods_describe);
		TextView time = (TextView) v.findViewById(R.id.goods_time);
		GridView gridview = (GridView) v.findViewById(R.id.gridview);
		TextView read_count = (TextView) v.findViewById(R.id.message_board_read);
		Button messgae_discuss = (Button) v.findViewById(R.id.messgae_discuss);
		LinearLayout conview = (LinearLayout) v.findViewById(R.id.conview);

		messgae_discuss.setOnClickListener(new on_Click());
		conview.setOnClickListener(new on_Click());
		read_count.setText("(" + goods.getRead() + ")");
		messgae_discuss.setText("(" + goods.getDiscuss() + ")");

		if (map.containsKey(String.valueOf(goods.getId()))) {
			ArrayList<Bitmap> arrBitmap = map.get(String.valueOf(goods.getId()));
			AddPicAdapter adapter = new AddPicAdapter(context);
			gridview.setAdapter(adapter);
			for (int i = 0; i < arrBitmap.size(); i++) {
				adapter.addItem(arrBitmap.get(i));
			}
		}

		String str = goods.getDescribe();
		String zhengze = "f0[0-9]{2}|f10[0-7]"; // 正则表达式，用来判断消息内是否有表情
		try {
			SpannableString spannableString = ExpressionUtil.getExpressionString(context, str, zhengze);
			describe.setText(spannableString);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		time.setText(goods.getTime());
		title.setText(goods.getName());
		return v;
	}

	public void mynotify() {
		notifyDataSetChanged();

	}

	class on_Click implements OnClickListener {
		final int p = index;

		@Override
		public void onClick(View v) {

			activity.onItem(v.getId(), getItem(p));
		}

	}
}