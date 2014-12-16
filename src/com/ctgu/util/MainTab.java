package com.ctgu.util;

import com.ctgu.ctguhelp.R;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class MainTab {
	TextView main_tab_unread_1, main_tab_unread_2, main_tab_unread_3, main_tab_unread_4;
	Activity context;

	public MainTab(Activity context) {
		super();
		this.context = context;
		main_tab_unread_1 = (TextView) context.findViewById(R.id.main_tab_unread_1);
		main_tab_unread_2 = (TextView) context.findViewById(R.id.main_tab_unread_2);
		main_tab_unread_3 = (TextView) context.findViewById(R.id.main_tab_unread_3);
		main_tab_unread_4 = (TextView) context.findViewById(R.id.main_tab_unread_4);
	}

	public TextView getMain_tab_unread_1() {
		return main_tab_unread_1;
	}

	public void setMain_tab_unread_1(int number) {
		main_tab_unread_1.setText(String.valueOf(number));
		main_tab_unread_1.setVisibility(View.VISIBLE);
	}

	public TextView getMain_tab_unread_2() {
		return main_tab_unread_1;
	}

	public void setMain_tab_unread_2(int number) {
		main_tab_unread_2.setText(String.valueOf(number));
		main_tab_unread_2.setVisibility(View.VISIBLE);
	}

	public TextView getMain_tab_unread_3() {
		return main_tab_unread_1;
	}

	public void setMain_tab_unread_3(int number) {
		main_tab_unread_3.setText(String.valueOf(number));
		main_tab_unread_3.setVisibility(View.VISIBLE);
	}

	public TextView getMain_tab_unread_4() {
		return main_tab_unread_4;
	}

	public void setMain_tab_unread_4(int number) {
		main_tab_unread_4.setText(String.valueOf(number));
		main_tab_unread_4.setVisibility(View.VISIBLE);
	}
}
