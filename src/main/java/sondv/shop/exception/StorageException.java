package sondv.shop.exception;

public class StorageException extends RuntimeException {

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String string, Exception e) {
		super(string, e);
	}

}
