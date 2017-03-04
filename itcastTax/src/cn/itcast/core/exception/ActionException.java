package cn.itcast.core.exception;

public class ActionException extends Exception {

	public ActionException() {
		super("请求操作失败");
		// TODO Auto-generated constructor stub
	}

	public ActionException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
