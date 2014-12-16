package com.ctgu.adapter;

import java.util.ArrayList;
import java.util.LinkedList;

import com.ctgu.ctguhelp.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class AddPicAdapter extends BaseAdapter {
	private LinkedList<Bitmap> barr;
	private Context context;

	public AddPicAdapter(Context context) {
		this.context = context;
		barr = new LinkedList<Bitmap>();
	}

	public void addItem(Bitmap b) {
		barr.addFirst(b);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {

		return barr.size();
	}

	@Override
	public Object getItem(int position) {

		return barr.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	public LinkedList<Bitmap> getList() {

		return barr;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(context);
			imageView.setLayoutParams(new GridView.LayoutParams(px2dip(90), px2dip(90)));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(px2dip(4), px2dip(4), px2dip(4), px2dip(4));
		} else {
			imageView = (ImageView) convertView;
		}
		// View v= View.inflate(context, R.layout.addpic_item, null);
		// ImageView imageview = (ImageView) v.findViewById(R.id.image);
		imageView.setImageBitmap(barr.get(position));
		return imageView;
	}

	public int px2dip(float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
