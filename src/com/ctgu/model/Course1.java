/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package com.ctgu.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Course1 implements Parcelable {
	public static final Parcelable.Creator<Course1> creator = new Creator() {
		@Override
		public Course1 createFromParcel(Parcel course) {
			return new Course1(course);
		}

		@Override
		public Course1[] newArray(int size) {
			return new Course1[size];
		}
	};
	private String condition;
	private String conditionId;
	private String course;
	private String pingjiaoOperate;
	private String pingjiaoOperateId;
	private String teacher;

	public Course1(String course, String teacher, String pingjiaoOperate,
			String pingjiaoOperateId, String condition, String conditionId) {
		this.course = course;
		this.teacher = teacher;
		this.pingjiaoOperate = pingjiaoOperate;
		this.pingjiaoOperateId = pingjiaoOperateId;
		this.condition = condition;
		this.conditionId = conditionId;
	}

	public Course1(Parcel in) {
		course = in.readString();
		teacher = in.readString();
		pingjiaoOperate = in.readString();
		pingjiaoOperateId = in.readString();
		condition = in.readString();
		conditionId = in.readString();
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getPingjiaoOperate() {
		return pingjiaoOperate;
	}

	public void setPingjiaoOperate(String pingjiaoOperate) {
		this.pingjiaoOperate = pingjiaoOperate;
	}

	public String getPingjiaoOperateId() {
		return pingjiaoOperateId;
	}

	public void setPingjiaoOperateId(String pingjiaoOperateId) {
		this.pingjiaoOperateId = pingjiaoOperateId;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(course);
		dest.writeString(teacher);
		dest.writeString(pingjiaoOperate);
		dest.writeString(pingjiaoOperateId);
		dest.writeString(condition);
		dest.writeString(conditionId);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}