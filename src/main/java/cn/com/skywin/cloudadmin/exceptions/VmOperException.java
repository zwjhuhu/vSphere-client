package cn.com.skywin.cloudadmin.exceptions;

public class VmOperException extends BaseException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8703086568460121088L;

	public VmOperException(int errCode,String message) {
		super(errCode,message);
	}
	
	public VmOperException(int errCode,Throwable t) {
		super(errCode,t);
	}
	
	public VmOperException(int errCode,String message,Throwable t) {
	    super(errCode,message,t);
	}
	
	public VmOperException(int errCode) {
		super(errCode);
	}

}
