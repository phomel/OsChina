package com.joy.oschina.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 图片url集合Bean
 */
public class ImageUrl implements Serializable{
	
	private static final long serialVersionUID = -4643899184080903646L;
	
	private List<String> urlList;
	
	public ImageUrl(List<String> urlList) {
		this.urlList = urlList;
	}
	
	public List<String> getUrlList() {
		return urlList;
	}
	
	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}
	
}
