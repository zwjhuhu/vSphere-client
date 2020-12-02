package cn.com.skywin.cloudadmin.exceptions;

/**
 * 基础异常类
 * @author zwj
 * @date 2020/03/10
 */
public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1737357388616529129L;
	
	protected int errCode;

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	
	public BaseException(int errCode,String message) {
		super(message);
		this.errCode = errCode;
	}
	
	public BaseException(int errCode,Throwable t) {
		super(t);
		this.errCode = errCode;
	}
	
	public BaseException(int errCode,String message,Throwable t) {
		super(message,t);
		this.errCode = errCode;
	}
	
	public BaseException(int errCode) {
		super();
		this.errCode = errCode;
	}
	

}
