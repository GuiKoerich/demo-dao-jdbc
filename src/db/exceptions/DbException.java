package db.exceptions;

public class DbException extends RuntimeException {

	private static final long serialVersionUID = -4217706507184874480L;
	
	public DbException(String msg) {
		super(msg);
	}
}
