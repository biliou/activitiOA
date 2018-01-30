package com.cypher.activiti.model;

import java.util.List;

public class AreaTree extends Area {

	private List<Area> chileAreaList;

	public List<Area> getChileAreaList() {
		return chileAreaList;
	}

	public void setChileAreaList(List<Area> chileAreaList) {
		this.chileAreaList = chileAreaList;
	}

	
}
