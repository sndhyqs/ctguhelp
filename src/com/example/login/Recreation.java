package com.example.login;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ctgu.base.BaseActivity;
import com.ctgu.base.BaseUiAuth;
import com.ctgu.ctguhelp.R;

public class Recreation extends BaseUiAuth {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recreation);

		init();

	}

	private void init() {
		// ImageView campus_activity = (ImageView)
		// findViewById(R.id.campus_activity);
		ImageView tuchao = (ImageView) findViewById(R.id.tuchao);
		// ImageView jianzhi = (ImageView) findViewById(R.id.jianzhi);
		ImageView trade = (ImageView) findViewById(R.id.trade);

		// campus_activity.setOnClickListener(new ViewClick());
		tuchao.setOnClickListener(new ViewClick());
		// jianzhi.setOnClickListener(new ViewClick());
		trade.setOnClickListener(new ViewClick());
	}

	class ViewClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
//			case R.id.campus_activity:
//				break;
//			case R.id.jianzhi:
//				break;
			case R.id.tuchao:
				forward_not_finish(Message_Board_Test.class);
				break;
			case R.id.trade:
				forward_not_finish(Transactions.class);
				break;
			}

		}

	}
}
