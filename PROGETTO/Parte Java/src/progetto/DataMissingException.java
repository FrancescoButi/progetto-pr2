package progetto;

//DatamissingException, lanciata quando non si trovano i dati richiesti

public class DataMissingException extends Exception {	
	public DataMissingException(String msg) {
		super(msg);
	}
}