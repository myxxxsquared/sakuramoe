package sakuramoe;

public class OperationResult {
	public boolean success;
	public Object result;
	public String reason;

	OperationResult(Object result, boolean success, String reason) {
		this.success = success;
		this.result = result;
		this.reason = reason;
	}
}
