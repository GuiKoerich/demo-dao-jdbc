package db.exceptions;

public class DbIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 4678634745681897569L;
	
	public DbIntegrityException(String msg) {
		super(msg);
	}
}
