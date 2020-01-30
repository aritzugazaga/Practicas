package orders;

public class OrderSerializationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderSerializationException(String message) {
		super(message);
	}
	
	public OrderSerializationException(String message, Throwable e) {
		super(message, e);
	}
}
