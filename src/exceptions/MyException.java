package exceptions;

public class MyException extends Exception{

	private static final long serialVersionUID = 3228493865631177902L;
	
	public MyException() {
		super("Invalid configuration");
	}
	public MyException(String msg) {
		super(msg);
	}

}
