package com.example.aioucomplaintcenter;

import javax.xml.namespace.QName;

public class RegDataObj {
	
	private String name
			,phn_number
			,program
			,reg_number
			,roll_number
			;
	
	public RegDataObj(String name, String phn_number, String program, String reg_number, String roll_number) {
		this.name = name;
		this.phn_number = phn_number;
		this.program = program;
		this.reg_number = reg_number;
		this.roll_number = roll_number;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPhn_number() {
		return phn_number;
	}
	
	public String getProgram() {
		return program;
	}
	
	public String getReg_number() {
		return reg_number;
	}
	
	public String getRoll_number() {
		return roll_number;
	}
}

