package com.ctgu.model;

public class Course {
	private String condition;
	private String conditionId;
	private String course;
	private String pingjiaoOperate;
	private String pingjiaoOperateId;
	private String teacher;

	public Course(String course, String teacher, String pingjiaoOperate, String pingjiaoOperateId, String condition, String conditionId) {
		this.course = course;
		this.teacher = teacher;
		this.pingjiaoOperate = pingjiaoOperate;
		this.pingjiaoOperateId = pingjiaoOperateId;
		this.condition = condition;
		this.conditionId = conditionId;
	}

	public Course(String course, String pingjiaoOperate, String pingjiaoOperateId, String teacher) {
		super();
		this.course = course;
		this.pingjiaoOperate = pingjiaoOperate;
		this.pingjiaoOperateId = pingjiaoOperateId;
		this.teacher = teacher;
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
	public String toString() {
		return "Course [condition=" + condition + ", conditionId=" + conditionId + ", course=" + course + ", pingjiaoOperate=" + pingjiaoOperate + ", pingjiaoOperateId=" + pingjiaoOperateId
				+ ", teacher=" + teacher + "]";
	}

}