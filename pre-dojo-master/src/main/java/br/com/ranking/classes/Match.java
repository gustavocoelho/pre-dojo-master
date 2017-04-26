package br.com.ranking.classes;

import java.util.Date;

public class Match {
	
	private Long id;
	private Date date;
	
	public Match(Long id, Date date){
		this.id = id;
		this.date = date;		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
}
