package edu.uca.csci4490.chessgame.model.data;

import java.io.Serializable;

public class ErrorData implements Serializable {
	private String msg;

	public ErrorData(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

