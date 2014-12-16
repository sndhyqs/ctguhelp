

package com.ctgu.model;

public class MyCourse {
	private String name;
	private String place;
	private String teacher;
	private String time;

	public MyCourse(String name, String place, String teacher, String time) {
		super();
		this.name = name;
		this.place = place;
		this.teacher = teacher;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String toString() {
		return name + ", place=" + place + ", time=" + time + ", teacher="
				+ teacher + "]";
	}
}
