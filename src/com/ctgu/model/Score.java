

package com.ctgu.model;

public class Score implements Comparable {

	private float achiveCredit;
	private String course;
	private float credit;
	private String examType;
	private String score;
	private int semester;
	private int year;

	public Score(int year, int semester, String course, float credit,
			String examType, String score, float achiveCredit) {
		this.year = year;
		this.semester = semester;
		this.course = course;
		this.credit = credit;
		this.examType = examType;
		this.score = score;
		this.achiveCredit = achiveCredit;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public float getCredit() {
		return credit;
	}

	public void setCredit(float credit) {
		this.credit = credit;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public float getAchiveCredit() {
		return achiveCredit;
	}

	public void setAchiveCredit(float achiveCredit) {
		this.achiveCredit = achiveCredit;
	}

	@Override
	public int compareTo(Object o) {
		Score score = (Score) o;
		if (this.year > score.year) {
			return -3;
			
		}else if(this.year==score.year&&this.semester == score.semester){
			return 0;
		}else{
		return 1;
		}
	}

}
