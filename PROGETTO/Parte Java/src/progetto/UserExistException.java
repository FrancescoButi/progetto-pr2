package progetto;

//UserExistException, lanciata quando si tenta di inserire un utente gi� presente

public class UserExistException extends Exception{
	public UserExistException(String msg) {
		super(msg);
	}
}
