package progetto;

//UserNotFoundException, lanciata quando nessun id inserito corrisponde a quello richiesto

public class UserNotFoundException extends Exception {
	public UserNotFoundException(String msg) {
		super(msg);
	}
}