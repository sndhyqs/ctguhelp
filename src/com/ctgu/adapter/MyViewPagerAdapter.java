package com.ctgu.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ctgu.ctguhelp.R;
import com.ctgu.model.MyCourse;

public class MyViewPagerAdapter extends PagerAdapter {
	private List<View> listViews;
	private int date;
	private ArrayList<MyCourse> course;

	public MyViewPagerAdapter(List<View> listViews,int date,ArrayList<MyCourse> course) {
		this.listViews = listViews;
		this.date = date;
		this.course = course;
	}

	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) listViews.get(position));
	}

	public Object instantiateItem(ViewGroup container, int position) {
		View v = (View) listViews.get(position);
		date = (position + 0x1);
		IntView(v);
		container.addView(v, 0x0);
		return listViews.get(position);
	}

	private void IntView(View v) {
		TextView one_two_course_name = (TextView) v
				.findViewById(R.id.one_two_course_name);
		TextView one_two_course_place = (TextView) v
				.findViewById(R.id.one_two_course_place);
		TextView one_two_course_time = (TextView) v
				.findViewById(R.id.one_two_course_time);
		TextView one_two_course_teacher = (TextView) v
				.findViewById(R.id.one_two_course_teacher);
		one_two_course_name.setText(course.get(date).getName());
		one_two_course_place.setText(course.get(date).getPlace());
		one_two_course_time.setText(course.get(date).getTime());
		one_two_course_teacher.setText(course.get(date).getTeacher());
		TextView three_four_course_name = (TextView) v
				.findViewById(R.id.three_four_course_name);
		TextView three_four_course_place = (TextView) v
				.findViewById(R.id.three_four_course_place);
		TextView three_four_course_time = (TextView) v
				.findViewById(R.id.three_four_course_time);
		TextView three_four_course_teacher = (TextView) v
				.findViewById(R.id.three_four_course_teacher);
		date = (date + 8);
		three_four_course_name.setText(course.get(date).getName());
		three_four_course_place.setText(course.get(date).getPlace());
		three_four_course_time.setText(course.get(date).getTime());
		three_four_course_teacher.setText(course.get(date).getTeacher());
		TextView five_six_course_name = (TextView) v
				.findViewById(R.id.five_six_course_name);
		TextView five_six_course_place = (TextView) v
				.findViewById(R.id.five_six_course_place);
		TextView five_six_course_time = (TextView) v
				.findViewById(R.id.five_six_course_time);
		TextView five_six_course_teacher = (TextView) v
				.findViewById(R.id.five_six_course_teacher);
		date = (date + 8);
		five_six_course_name.setText(course.get(date).getName());
		five_six_course_place.setText(course.get(date).getPlace());
		five_six_course_time.setText(course.get(date).getTime());
		five_six_course_teacher.setText(course.get(date).getTeacher());
		TextView seven_eight_course_name = (TextView) v
				.findViewById(R.id.seven_eight_course_name);
		TextView seven_eight_course_place = (TextView) v
				.findViewById(R.id.seven_eight_course_place);
		TextView seven_eight_course_time = (TextView) v
				.findViewById(R.id.seven_eight_course_time);
		TextView seven_eight_course_teacher = (TextView) v
				.findViewById(R.id.seven_eight_course_teacher);
		date = (date + 8);
		seven_eight_course_name.setText(course.get(date).getName());
		seven_eight_course_place.setText(course.get(date).getPlace());
		seven_eight_course_time.setText(course.get(date).getTime());
		seven_eight_course_teacher.setText(course.get(date).getTeacher());
		TextView nine_ten_course_name = (TextView) v
				.findViewById(R.id.nine_ten_course_name);
		TextView nine_ten_course_place = (TextView) v
				.findViewById(R.id.nine_ten_course_place);
		TextView nine_ten_course_time = (TextView) v
				.findViewById(R.id.nine_ten_course_time);
		TextView nine_ten_course_teacher = (TextView) v
				.findViewById(R.id.nine_ten_course_teacher);
		date = (date + 8);
		nine_ten_course_name.setText(course.get(date).getName());
		nine_ten_course_place.setText(course.get(date).getPlace());
		nine_ten_course_time.setText(course.get(date).getTime());
		nine_ten_course_teacher.setText(course.get(date).getTeacher());
	}

	public int getCount() {
		return listViews.size();
	}

	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}
}