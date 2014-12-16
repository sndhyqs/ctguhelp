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
import com.ctgu.adapter.TranionReviewAdapter;
import com.ctgu.base.BaseTask;
import com.ctgu.base.BaseUiAuth;
import com.ctgu.base.C;
import com.ctgu.ctguhelp.R;
import com.ctgu.model.Message;
import com.ctgu.model.Review;
import com.ctgu.model.TransactionsGoods;
import com.ctgu.util.ExpressionUtil;
import com.ctgu.util.IO;
import com.ctgu.util.XmlToString;

public class TranionConView extends BaseUiAuth {
	ListView list_review;
	private TransactionsGoods m;
	private int id;
	private TranionReviewAdapter adapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tranion_content_view);

		Button discuss = (Button) findViewById(R.id.contentViewDiscuss);
		TextView read = (TextView) findViewById(R.id.contentViewRead);
		TextView time = (TextView) findViewById(R.id.contentViewTime);
		TextView user = (TextView) findViewById(R.id.contentViewUser);
		TextView describe = (TextView) findViewById(R.id.contentViewContent);
		list_review = (ListView) findViewById(R.id.list_review);
		this.adapter = new TranionReviewAdapter(this, this);
		Bundle b = getIntent().getExtras();
		m = new TransactionsGoods(b.getInt("id"), b.getString("name"), b.getString("describe"), b.getString("time"), b.getString("picurl"), b.getString("tel"), b.getString("read"),
				b.getString("discuss"));
		String str = m.getDescribe();
		String zhengze = "f0[0-9]{2}|f10[0-7]"; // 正则表达式，用来判断消息内是否有表情
		try {
			SpannableString spannableString = ExpressionUtil.getExpressionString(this, str, zhengze);
			describe.setText(spannableString);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		discuss.setText("(" + m.getDiscuss() + ")");
		read.setText("(" + m.getRead() + ")");
		time.setText(m.getTime());
		user.setText(m.getName());

		getReview();
		discuss.setOnClickListener(new myClick());
		list_review.setAdapter(adapter);
	}

	private void getReview() {
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("content_id", String.valueOf(m.getId()));
		taskPool_ctguHelp.addTask(C.task.getReview, C.api.get_tranion_review, taskArgs, new BaseTask() {

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
				b.putInt("id", m.getId());
				b.putString("user", m.getName());
				forward(TranionDiscuss.class, b);
				break;
			}

		}
	}

	public void onItem(String id) {
		Bundle b = new Bundle();
		b.putString("id", id);
		b.putString("contentId", String.valueOf(m.getId()));
		forward_not_finish(TranionReviewEdit.class, b);
	}
}
