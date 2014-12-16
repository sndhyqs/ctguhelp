package com.ctgu.adapter;

import java.util.ArrayList;

import com.ctgu.ctguhelp.R;
import com.ctgu.model.Message;
import com.ctgu.model.Review;
import com.ctgu.util.ExpressionUtil;
import com.example.login.ConView;

import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReviewAdapter extends BaseAdapter {
	private ArrayList<Review> reviewArr;
	private Context context;
	private int index;
	private ConView c;

	public ReviewAdapter(Context context, ConView c) {
		super();
		this.reviewArr = new ArrayList<Review>();
		this.context = context;
		this.c = c;
	}

	public void setReviewArr(ArrayList<Review> reviewArr) {
		this.reviewArr = reviewArr;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return reviewArr.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return reviewArr.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		this.index = position;
		View v = View.inflate(context, R.layout.review_item, null);
		TextView review_user = (TextView) v.findViewById(R.id.review_user);
		TextView review_time = (TextView) v.findViewById(R.id.review_time);
		TextView review_content = (TextView) v.findViewById(R.id.review_content);
		TextView TVreview = (TextView) v.findViewById(R.id.review);

		Review review = (Review) reviewArr.get(position);

		if (review.getUser_review().equals("01")) {
			review_user.setText("      " + review.getDiscuss() + "回复" + review.getUser());

		} else {
			review_user.setText("      " + review.getDiscuss());
		}

		review_time.setText(review.getTime());
		String str = review.getContent();
		String zhengze = "f0[0-9]{2}|f10[0-7]"; // 正则表达式，用来判断消息内是否有表情
		try {
			SpannableString spannableString = ExpressionUtil.getExpressionString(context, str, zhengze);
			review_content.setText(spannableString);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		TVreview.setOnClickListener(new onClick());
		return v;
	}

	class onClick implements OnClickListener {
		final int p = index;

		@Override
		public void onClick(View v) {
			Review r = (Review) getItem(p);
			c.onItem(r.getId());
		}

	}
}
