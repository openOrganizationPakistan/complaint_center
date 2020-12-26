package com.example.aioucomplaintcenter;

public class DataObj {
	
	private String title
			,issue
			,date;
	
	public DataObj(String title, String issue, String date) {
		this.title = title;
		this.issue = issue;
		this.date = date;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getIssue() {
		return issue;
	}
	
	public String getDate() {
		return date;
	}
}
