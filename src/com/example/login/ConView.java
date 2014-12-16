package com.example.login;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ctgu.adapter.ReviewAdapter;
import com.ctgu.base.BaseTask;
import com.ctgu.base.BaseUiAuth;
import com.ctgu.base.C;
import com.ctgu.ctguhelp.R;
import com.ctgu.model.Message;
import com.ctgu.model.Review;
import com.ctgu.util.ExpressionUtil;
import com.ctgu.util.IO;
import com.ctgu.util.XmlToString;

public class ConView extends BaseUiAuth {
	ListView list_review;
	private Message m;
	private int id;
	private ReviewAdapter adapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_view);
		TextView content = (TextView) findViewById(R.id.contentViewContent);
		Button discuss = (Button) findViewById(R.id.contentViewDiscuss);
		Button good = (Button) findViewById(R.id.contentViewGood);
		TextView read = (TextView) findViewById(R.id.contentViewRead);
		TextView time = (TextView) findViewById(R.id.contentViewTime);
		TextView user = (TextView) findViewById(R.id.contentViewUser);
		list_review = (ListView) findViewById(R.id.list_review);
		this.adapter = new ReviewAdapter(this, this);
		Bundle b = getIntent().getExtras();
		this.m = new Message(b.getString("content"), b.getInt("id"), b.getString("time"), b.getString("user"), b.getInt("read"), b.getInt("good"), 0, b.getInt("discuss"));
		getReview();
		String str = m.getContent();
		String zhengze = "f0[0-9]{2}|f10[0-7]"; // 正则表达式，用来判断消息内是否有表情
		try {
			SpannableString spannableString = ExpressionUtil.getExpressionString(this, str, zhengze);
			content.setText(spannableString);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		discuss.setText("(" + String.valueOf(m.getDiscuss()) + ")");
		good.setText("(" + String.valueOf(m.getGood()) + ")");
		read.setText("(" + String.valueOf(m.getRead()) + ")");
		time.setText(m.getTime());
		user.setText(m.getUser());

		discuss.setOnClickListener(new myClick());
		good.setOnClickListener(new myClick());
		list_review.setAdapter(adapter);
	}

	private void getReview() {
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("content_id", String.valueOf(m.getId()));
		System.out.println(m.getId());
		taskPool_ctguHelp.addTask(C.task.getReview, C.api.getReview, taskArgs, new BaseTask() {

			@Override
			public void onComplete(InputStream inputStream) {

				ArrayList<Review> reviewArr = XmlToString.xml_to_review(inputStream);
				sendMessage(BaseTask.TASK_COMPLETE, C.task.getReview, reviewArr);
			}

			@Override
			public void onError(String error) {
				sendMessage(BaseTask.NETWORK_ERROR, C.task.getReview, null);
			}

		}, 0);
	}

	@Override
	public void onTaskComplete(int taskId, Object message) {
		switch (taskId) {
		case C.task.getReview:
			ArrayList<Review> reviewArr = (ArrayList<Review>) message;
			if (reviewArr.size() > 0) {
				adapter.setReviewArr(reviewArr);
				RelativeLayout user = (RelativeLayout) findViewById(R.id.re);
				user.setVisibility(View.INVISIBLE);
			}
		}
	}

	class myClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.contentViewDiscuss:
				Bundle b = new Bundle();
				b.putInt("key", m.getId());
				forward(Discuss.class, b);
				break;

			case R.id.contentViewGood:
				HashMap<String, String> taskArgs = new HashMap<String, String>();
				taskArgs.put("id", String.valueOf(m.getId()));
				taskArgs.put("user", user.getMy_userName());
				taskPool_ctguHelp.addTask(C.task.set_good, C.api.set_good, taskArgs, new BaseTask() {

					@Override
					public void onComplete(InputStream inputStream) {
						String data = "操作成功";
						sendMessage(BaseTask.TASK_COMPLETE, C.task.set_good, data);
					}

					@Override
					public void onError(String error) {
						sendMessage(BaseTask.NETWORK_ERROR, C.task.set_good, null);
					}

				}, 0);
				break;
			}

		}
	}

	public void onItem(String id) {
		Bundle b = new Bundle();
		b.putString("id", id);
		b.putString("contentId", String.valueOf(m.getId()));
		forward_not_finish(ReviewEdit.class, b);
	}
}
