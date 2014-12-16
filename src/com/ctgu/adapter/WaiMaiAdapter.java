package com.ctgu.adapter;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

import com.ctgu.ctguhelp.R;
import com.ctgu.model.Book;
import com.ctgu.model.Lost_Find_Message;
import com.ctgu.model.Message;
import com.ctgu.model.WaiMaiM;
import com.ctgu.util.AppCache;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 外卖商家页面的适配器
 * 
 * @author Administrator
 * 
 */
public class WaiMaiAdapter extends BaseAdapter {
	private Context conext;
	private ArrayList<WaiMaiM> waimai;

	public WaiMaiAdapter(Context conext) {
		this.waimai = new ArrayList<WaiMaiM>();
		this.conext = conext;
	}

	public void addItems(ArrayList<WaiMaiM> newItems) {
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

	public void mynotify() {
		notifyDataSetChanged();
	}

	public WaiMaiM getItem(int position) {
		return waimai.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		WaiMaiM w = waimai.get(position);
		View v = View.inflate(conext, R.layout.waimai_item, null);
		TextView title = (TextView) v.findViewById(R.id.title);
		ImageView pic = (ImageView) v.findViewById(R.id.pic);
		Bitmap bm = AppCache.getImage(w.getTubiao());
		if (bm != null) {
			pic.setImageBitmap(bm);
		}
		TextView waimai_number = (TextView) v.findViewById(R.id.waimai_number);
		title.setText(w.getName());
		waimai_number.setText(w.getTradeNumber());
		return v;
	}
}
