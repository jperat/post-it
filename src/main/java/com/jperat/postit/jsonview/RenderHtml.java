package com.jperat.postit.jsonview;

import com.fasterxml.jackson.annotation.JsonView;

public class RenderHtml {

	@JsonView(Views.Public.class)
	String msg;
	
	@JsonView(Views.Public.class)
	String code;
	
	@JsonView(Views.Public.class)
	String render;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRender() {
		return render;
	}

	public void setRender(String render) {
		this.render = render;
	}
	
}
