package progetto;

//librerie utili:

import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;

/*  overview: Primo metodo di implementazione dell'interfaccia SecureDataContainer, uso HashMap
	
	IR = 
		Utente != null 
		&& DatiUtente != null 
		&& utente.length() == DatiUtente.length() 
		&& DatiUtente.value != null  
		&& (( (forall i,j in utente) \ (i = utente.key and j = utente.value) => (i != null || j != null ||  a <= i.lenght() <= b || a <= j.length() <= b))
		&& a=5 (limite minimo)
		&& b=20 (limite massimo)

 	AF = (utente ,DatiUtente) dove :

 			- utente compone una Hash map di coppie di string: HashMap<String,String> 
			-DatiUtente compone una Hash map di coppie di string e arraylist: HashMap<String,ArrayList<E>>
			
			generico <E>: � un tipo generico di dato che user� come dati utente

 */


//la classe MySecureDataContainer
public class MySecureDataContainer<E> implements SecureDataContainer<E> {
//Hash map per l'utente
	private HashMap<String,String> utente = new HashMap<String,String>();
//Hash map parallela per i dati utente
	private HashMap<String,ArrayList<E>> DatiUtente = new HashMap<String,ArrayList<E>>();
	
	


/*	METODO CREATEUSER:
	EFFECTS: genera un utente da inserire se non ne esiste gi� uno con lo stesso username
	MODIFIES: DatiUtente, Utente
	THROWS: IllegalArgumentException se Id == null o passw == null 
			UserExistException se esiste gi� un utente registrato con quell'id

*/

	public void createUser(String Id, String passw) throws IllegalArgumentException,UserExistException {

		if(!this.isValidValue(Id, passw)) throw new IllegalArgumentException("lo username e la password devono essere"
				+ " di lunghezza compresa tra 5 e 20 caratteri");

		if(this.userExists(Id)) throw new UserExistException("L'utente � gi� registrato! ");

		utente.put(Id, passw);
		DatiUtente.put(Id, new ArrayList<E>());

	}



/*	METODO GETSIZE:
	EFFECTS: verifica username e password e, se il match corrisponde, restituisce il numeri di dati dell'utente
	MODIFIES: -
	THROWS:	IllegalArgumentException se Owner == null o passw == null 
			UserNotFoundException se non esiste corrispondenza tra username e password forniti
 */

	public int getSize(String Owner, String passw) throws UserNotFoundException {

		

		if(!this.isValidValue(Owner, passw)) throw new IllegalArgumentException("lo username e la password devono essere"
				+ " di lunghezza compresa tra 5 e 20 caratteri");

		if(!this.userExists(Owner, passw)) throw new UserNotFoundException("Username o password errati!");

		return DatiUtente.get(Owner).size();

}


/*	METODO PUT:
	EFFECTS: verifica username e password e, se il match corrisponde,inserisce il dato tra i dati utente
	MODIFIES: this.data
	THROWS:	IllegalArgumentException se Owner == null o passw == null o l'oggetto data == null
 			UserNotFoundException se non esiste corrispondenza tra username e password forniti
*/

	public boolean put(String Id, String password, E data) throws UserNotFoundException {

		

		if(!this.isValidValue(Id, password)) throw new IllegalArgumentException("lo username e la password devono essere"
						+ " di lunghezza compresa tra 5 e 20 caratteri");

		if(data == null) throw new IllegalArgumentException("Oggetto non valido!");

		if(!this.userExists(Id, password)) throw new UserNotFoundException("Username o password errati!");

		

		return DatiUtente.get(Id).add(data);

	}

/*	METODO GET:
	EFFECTS: verifica username e password e, se il match corrisponde, restituisce i dati dell'utente
	MODIFIES: -
	THROWS:	IllegalArgumentException se Owner == null o passw == null o l'oggetto data == null
			UserNotFoundException se non esiste corrispondenza tra username e password forniti
			DataMissingException se non � possibile trovare nei dati dell'utente l'oggetto

*/
	
	public E get(String Owner, String passw, E data) throws UserNotFoundException, DataMissingException {

		

		if(!this.isValidValue(Owner, passw)) throw	new IllegalArgumentException("lo username e la password devono essere"
				+ " di lunghezza compresa tra 5 e 20 caratteri");
		
		if(!this.userExists(Owner, passw)) throw new UserNotFoundException("Username o password errati!");

		E dataValid = null;

		

		for(int i = 0; i<DatiUtente.get(Owner).size();i++) if(DatiUtente.get(Owner).get(i).equals(data)) 

				dataValid =DatiUtente.get(Owner).get(i);


		if(dataValid == null) throw new DataMissingException("Oggetto non trovato!");

		return dataValid;

	}
	
/*	METODO REMOVE:
	EFFECTS: verifica username e password e, se il match corrisponde,rimuove l'utente con tutti i suoi dati
	MODIFIES: this
	THROWS:	IllegalArgumentException se Owner == null o passw == null o l'oggetto data == null
			UserNotFoundException se non esiste corrispondenza tra username e password forniti
			DataMissingException se non � possibile trovare nei dati dell'utente l'oggetto
*/

	public E remove(String Owner, String passw, E data) throws UserNotFoundException, DataMissingException {

		

		if(!this.isValidValue(Owner, passw)) throw new IllegalArgumentException("lo username e la password devono essere"
						+ " di lunghezza compresa tra 5 e 20 caratteri");

		

		if(data == null)

			throw

				new IllegalArgumentException("Oggetto non valido!");

		

		if(!this.userExists(Owner, passw)) 

			throw 

				new UserNotFoundException("Username o password errati!");

		

		boolean dataFound = false;

		for(int i = 0; i<DatiUtente.get(Owner).size();i++) 

			if(DatiUtente.get(Owner).get(i).equals(data)) {

				DatiUtente.get(Owner).remove(i);

				dataFound = true;

			}

		

		if(!dataFound)

			throw 

				new DataMissingException("Oggetto non trovato !");



		return 

			data;

			

	}
	
/*	METODO COPY:
	EFFECTS: vegono validati i dati utente
	MODIFIES: DatiUtente
	THROWS: IllegalArgumentException se Owner == null o passw == null o l'oggetto data == null
			UserNotFoundException se non esiste corrispondenza tra username e password forniti
			DataMissingException se non � possibile trovare nei dati dell'utente l'oggetto da copiare
*/

	public void copy(String Owner, String passw, E data) throws IllegalArgumentException,UserNotFoundException,DataMissingException {
		if(!this.isValidValue(Owner, passw)) throw new IllegalArgumentException("lo username e la password devono essere"
				+ " di lunghezza compresa tra 5 e 20 caratteri");

		if(!this.userExists(Owner, passw))throw new UserNotFoundException("Username o password errati!");

		if(data == null) throw new IllegalArgumentException("Oggetto non valido!");

		

		E dataValid = null;

		

		for(int i = 0; i<DatiUtente.get(Owner).size();i++) 

			if(DatiUtente.get(Owner).get(i).equals(data))  dataValid =DatiUtente.get(Owner).get(i);

			
		if(dataValid == null) throw new DataMissingException("Oggetto non trovato!");
		DatiUtente.get(Owner).add(dataValid);

}
	
/*	METODO SHARE:
	EFFECTS: verifica gli username e le password e, se il match corrisponde, condivide il dato tra i due utenti
	MODIFIES: -
	THROWS:	IllegalArgumentException se Owner == null o passw == null o l'oggetto data == null
			UserNotFoundException se non esiste corrispondenza tra username e password forniti
			DataMissingException se non � possibile trovare nei dati dell'utente l'oggetto
 */
	public void share(String Owner, String passw, String Other, E data) throws UserNotFoundException{

		

		if(!this.isValidValue(Owner, passw)) throw new IllegalArgumentException("lo username e la password devono essere"
				+ " di lunghezza compresa tra 5 e 20 caratteri");

		if(Other == null) throw new IllegalArgumentException("Utente inserito per la condivisione non presente");
		
		if(data == null) throw new IllegalArgumentException("Oggetto non valido!");
		
		if(!this.userExists(Other)) throw new UserNotFoundException("Utente selezionato per la condivisione non trovato! ");

		if(!this.userExists(Owner, passw)) throw new UserNotFoundException("Utente non presente nella lista!");

		

		boolean dataExist = false;

	

		for(int i = 0; i<DatiUtente.get(Owner).size();i++) 

			if(DatiUtente.get(Owner).get(i).equals(data)) 

				dataExist = true;

			

		if(!dataExist) 

			DatiUtente.get(Owner).add(data);

	

		DatiUtente.get(Other).add(data);



	}


	
/*	METODO GETITERATOR:
	EFFECTS: verifica username e password e, se il match corrisponde, restituisce l'iteratore
	MODIFIES: -
	THROWS:	IllegalArgumentException se Owner == null o passw == null 
			UserNotFoundException se non esiste corrispondenza tra username e password forniti
*/


	public Iterator<E> getIterator(String Owner, String passw) throws UserNotFoundException {

		

		if(!this.isValidValue(Owner, passw)) throw new IllegalArgumentException("lo username e la password devono essere"
				+ " di lunghezza compresa tra 5 e 20 caratteri");

		if(!this.userExists(Owner, passw)) throw new UserNotFoundException("Username o password errati!");

		List<E> list =DatiUtente.get(Owner);

		return Collections.unmodifiableList(list).iterator();

}
	
//========================================================================================================================================
/*
 * METODI UTILI CHE NON IMPLEMENTANO L'INTERFACCIA MA FANNO DA SUPPORTO PER GLI ALTRI	
 */


/*	METODO VALID:
	EFFECTS: verifica la correttezza della dimensione di username e password 
	MODIFIES: -
	THROWS:	-
*/

	private boolean isValidValue(String Id , String passw) {

		

		return 

				!(Id == null || passw == null || Id.length() < 5 || Id.length() > 20 

					|| passw.length() <= 5 || passw.length() >= 20);

	}

/*	METODO GETSIZE:
	EFFECTS: verifica username e password e, se il match corrisponde, restituisce il numero di oggetti
			 identici a quello passato per parametro nella collezione
	MODIFIES: -
	THROWS:	IllegalArgumentException se Owner == null o passw == null o l'oggetto data == null
			UserNotFoundException se non esiste corrispondenza tra username e password forniti
			DataMissingException se non � possibile trovare nei dati dell'utente l'oggetto

*/

	public int numberOf(String Owner, String passw, E data) throws UserNotFoundException {

		

		if(!this.isValidValue(Owner, passw)) throw new IllegalArgumentException("lo username e la password devono essere"
						+ " di lunghezza compresa tra 5 e 20 caratteri");

		if(data == null)
			throw new IllegalArgumentException("Oggetto non valido!");

		if(!this.userExists(Owner, passw)) throw new UserNotFoundException("Utente non presente nella lista!");

		int cont = 0;

		for(int i = 0; i<DatiUtente.get(Owner).size();i++) 
			if(DatiUtente.get(Owner).get(i).equals(data)) 
				cont++;		

		return cont;

	}






/*
	METODO USEREXISTS
	EFFECTS: Controlla se esiste un utente con l'id inserito
 	MODIFIES: -
 	THROWS: -

*/

	public boolean userExists(String Id) {		

		for(Map.Entry<String, String> key : utente.entrySet()) 

			if(key.getKey() == Id)return true;

		return false;

	}

/*

	EFFECTS: Controlla se esiste un utente con id e password uguali a quelli passati per parametro
 	MODIFIES: -
 	THROWS: -

*/

	public boolean userExists(String Id, String passw) {

		

		for(Map.Entry<String, String> key : utente.entrySet()) 

			if(key.getKey() == Id && key.getValue() == passw) return true;

		return false;

		

	}



}