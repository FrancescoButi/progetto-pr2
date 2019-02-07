package progetto;

//UserExistException, lanciata quando si tenta di inserire un utente già presente

public class UserExistException extends Exception{
	public UserExistException(String msg) {
		super(msg);
	}
}
