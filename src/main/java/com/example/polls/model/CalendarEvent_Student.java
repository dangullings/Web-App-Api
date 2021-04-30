package com.example.polls.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "calendar_events_students")
@IdClass(Calendar_Event_Student_Id.class)
public class CalendarEvent_Student {

	@Id
    private long calendarEventId;
	
	@Id
    private long studentId;
	
	private BigDecimal paid;
	
	private BigDecimal charged;
	
	private String signupDate;

	public CalendarEvent_Student() {
		super();
	}

	public long getCalendarEventId() {
		return calendarEventId;
	}

	public void setCalendarEventId(long calendarEventId) {
		this.calendarEventId = calendarEventId;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public BigDecimal getPaid() {
		return paid;
	}

	public void setPaid(BigDecimal paid) {
		this.paid = paid;
	}

	public BigDecimal getCharged() {
		return charged;
	}

	public void setCharged(BigDecimal charged) {
		this.charged = charged;
	}

	public String getSignupDate() {
		return signupDate;
	}

	public void setSignupDate(String signupDate) {
		this.signupDate = signupDate;
	}
	
	
}
