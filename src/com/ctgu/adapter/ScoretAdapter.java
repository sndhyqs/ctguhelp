package com.ctgu.adapter;

import android.widget.BaseAdapter;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import com.ctgu.ctguhelp.R;
import com.ctgu.model.Score;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScoretAdapter extends BaseAdapter {
	private Context context;
	private List<Score> score;

	public ScoretAdapter(Context context) {
		this.score =new ArrayList<Score>();
		this.context = context;
	}

	public int getCount() {
		return score.size();
	}
	
	public List<Score> getScore() {
		return score;
	}

	public void setScore(List<Score> score) {
		this.score = score;
		notifyDataSetChanged();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Score info = (Score) score.get(position);
		View view = View.inflate(context, R.layout.score_item, null);
		TextView tv_course = (TextView) view.findViewById(R.id.tv_course);
		TextView tv_score = (TextView) view.findViewById(R.id.tv_score);
		tv_course.setText("   课程名："+info.getCourse());
		tv_score.setText("  分数 ：  "+info.getScore());
		return view;
	}
}
