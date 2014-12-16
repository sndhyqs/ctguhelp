package com.ctgu.adapter;

import android.widget.BaseAdapter;
import android.content.Context;
import java.util.ArrayList;

import com.ctgu.ctguhelp.R;
import com.ctgu.model.WaiMaiGoods;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 外卖商家商品页面适配器
 * 
 * @author Administrator
 * 
 */
public class WaiMaiGoodsAdapter extends BaseAdapter {
	private Context conext;
	private ArrayList<WaiMaiGoods> waimai;

	public WaiMaiGoodsAdapter(Context conext) {
		this.waimai = new ArrayList<WaiMaiGoods>();
		this.conext = conext;
	}

	public void addItems(ArrayList<WaiMaiGoods> newItems) {
		if ((newItems == null) || (newItems.size() <= 0)) {
			return;
		}
		for (int i = 0; i < newItems.size(); i++) {
			waimai.add(newItems.get(i));
		}

		notifyDataSetChanged();
	}

	public int getCount() {
		return waimai == null ? 0 : waimai.size();
	}

	

	public void clear() {
		waimai.clear();
		notifyDataSetChanged();
	}

	public WaiMaiGoods getItem(int position) {
		return waimai.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		WaiMaiGoods w = waimai.get(position);
			View v = View.inflate(conext, R.layout.waimaigoods_item, null);
			TextView title = (TextView) v.findViewById(R.id.title);
			TextView price = (TextView) v.findViewById(R.id.price);
			title.setText(w.getName());
			price.setText(w.getPrice());
			return v;
		

	}
}
