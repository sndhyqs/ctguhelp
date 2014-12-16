package com.example.login;

import com.ctgu.adapter.ScoretAdapter;
import com.ctgu.base.BaseTask;
import com.ctgu.base.BaseUiAuth;
import com.ctgu.base.C;
import com.ctgu.ctguhelp.R;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.Header;

import com.ctgu.model.Score;
import com.ctgu.util.AsyncHttp;
import com.ctgu.util.IO;
import com.ctgu.util.XmlToString;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class List_Score extends BaseUiAuth {
	ListView lv_score;
	List_Score instance = null;
	ScoretAdapter scoreAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_course);
		TextView mytitle =(TextView) findViewById(R.id.mytitle);
		mytitle.setText("成绩");
		instance = this;
		lv_score = (ListView) findViewById(R.id.list);
		scoreAdapter = new ScoretAdapter(this);
		contentShow();
	}

	public void contentShow() {
		scoreAdapter = new ScoretAdapter(this);
		lv_score.setAdapter(scoreAdapter);
		pd.show();
		taskPool.addTask(C.task.getScore, C.api.getScore, new BaseTask() {
			@Override
			public void onComplete(InputStream inputStream) {
				String result = IO.inputSreamToString(inputStream);
				sendMessage(BaseTask.TASK_COMPLETE, C.task.getScore, result);
			}
		}, 0);

	}

	public void onTaskComplete(int taskId, Object message) {
		if (pd.isShowing()) {
			pd.dismiss();
		}
		String html = (String) message;

		switch (taskId) {
		case C.task.getScore:
			FileOutputStream fout = null;
			ArrayList<Score> scores = (ArrayList<Score>) XmlToString.scoreTo(html);
			File file = new File(getCacheDir(), "score.xml");
			try {
				fout = new FileOutputStream(file);
			} catch (Exception e) {
				e.printStackTrace();
			}

			XmlToString.save_xml_sorce(scores, fout);
			RequestParams params = new RequestParams();
			try {
				params.put("scores", file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			params.put("userNumber", user.getUserNumber());
			params.put("type", "2");
			AsyncHttp.Post(C.api.uploadscore, params, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

				}
			});
			scoreAdapter.setScore(scores);
			break;

		}
	}
}
