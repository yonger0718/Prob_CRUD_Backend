package tw.com.cathaybk.exception;


public class UploadFailureException extends RuntimeException {
    public UploadFailureException(String message) {
        super(message);
    }
}
