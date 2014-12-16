package com.example.login;

import com.ctgu.base.BaseActivity;
import com.ctgu.base.BaseTask;
import com.ctgu.ctguhelp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TranionRightDialog extends BaseActivity {
	public class LayOnClickListener implements OnClickListener {

		private int index = 0;

		public LayOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			switch (index) {
			case 0:
				forward(AddTranion.class);
				break;
			case 1:
				forward(TranionMyMessage.class);
				break;
			case 2:
				forward(SendTransactions.class);
				break;
			}
		}
	};

	// private MyDialog dialog;
	private LinearLayout send_message, receive_message, my_message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_top_right_dialog);
		send_message = (LinearLayout) findViewById(R.id.send_message);
		receive_message = (LinearLayout) findViewById(R.id.receive_message);
		my_message = (LinearLayout) findViewById(R.id.my_message);

		send_message.setOnClickListener(new LayOnClickListener(0));
		receive_message.setOnClickListener(new LayOnClickListener(1));
		my_message.setOnClickListener(new LayOnClickListener(2));

	}

}
