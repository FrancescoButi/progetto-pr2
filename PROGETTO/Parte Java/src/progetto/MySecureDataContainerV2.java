package progetto;

/*  overview: Secondo metodo di implementazione dell'interfaccia SecureDataContainer, uso Vector

IR = 
	Utente != null 
	&& Utente.getId() != null 
	&& utente.getPassword() == DatiUtente.length()  
	&& 6<utente.getPassword.lenght()<16
	&& 6<utente.getId.lenght()<16

	AF = (utente ,DatiUtente) dove :

		- utente è implementato tramite un vector di Utente
*/

	import java.util.Collections;

	import java.util.Iterator;

	import java.util.List;

	import java.util.Vector;

	public class MySecureDataContainerV2 <E> implements SecureDataContainer<E> {



		private Vector<Utente<E>> Utente = new Vector<Utente<E>>();

/*	METODO COPY:
		EFFECTS: vegono validati i dati utente
		MODIFIES: DatiUtente
		THROWS: IllegalArgumentException se Owner == null o passw == null o l'oggetto data == null
				UserNotFoundException se non esiste corrispondenza tra username e password forniti
				DataMissingException se non è possibile trovare nei dati dell'utente l'oggetto da copiare
*/

		public void copy(String Owner, String passw, E data) 

				throws IllegalArgumentException,UserNotFoundException,DataMissingException {

			

			if(!this.isValidValue(Owner,passw))

				throw 

					new IllegalArgumentException("Username o password non validi! "

							+ "La lunghezza dello username e della password deve essere "

							+ "compesa tra 6 e 16 ");

			

			if(data == null)

				throw 

					new IllegalArgumentException("Oggetto non valido!");

			

			Utente<E> tmp = this.userExists(Owner, passw);

			

			if(tmp == null)

				throw 

					new UserNotFoundException("Username o password errati !");

			

			boolean found = tmp.IsIn(data);

			

			if(!found)

				throw

					new DataMissingException("Oggetto non trovato");

			

			tmp.addToList(data);

			

		}

/*	METODO CREATEUSER:
		EFFECTS: genera un utente da inserire se non ne esiste già uno con lo stesso username
		MODIFIES: Utente
		THROWS: IllegalArgumentException se Id == null o passw == null 
				UserExistException se esiste già un utente registrato con quell'id

*/

		public void createUser(String Id, String passw) 

				throws IllegalArgumentException,UserExistException {

			

			if(!this.isValidValue(Id, passw))

				throw new IllegalArgumentException("Username o password non validi! "

						+ "La lunghezza dello username e della password deve essere "

						+ "compesa tra 6 e 16 ");

			

			if(this.userExists(Id) != null)

				throw new UserExistException("Utente già registrato!");

			

			Utente.add(new Utente<E>(Id,passw));

		}

/*	METODO GET:
		EFFECTS: verifica username e password e, se il match corrisponde, restituisce i dati dell'utente
		MODIFIES: -
		THROWS:	IllegalArgumentException se Owner == null o passw == null o l'oggetto data == null
				UserNotFoundException se non esiste corrispondenza tra username e password forniti
				DataMissingException se non è possibile trovare nei dati dell'utente l'oggetto

*/
		public E get(String Owner, String passw, E data) 

				throws IllegalArgumentException,UserNotFoundException,DataMissingException {

			

			if(!this.isValidValue(Owner,passw))

				throw 

					new IllegalArgumentException("Username o password non validi! "

							+ "La lunghezza dello username e della password deve essere "

							+ "compesa tra 6 e 16 ");

			

			if(data == null)

				throw 

					new IllegalArgumentException("Oggetto non valido!");

			

			Utente<E> tmp = this.userExists(Owner, passw);

			

			if(tmp == null)

				throw 

					new UserNotFoundException("Username o password errati !");

			

			boolean found = tmp.addToList(data);

			

			if(!found)

				throw

					new DataMissingException("Oggetto non trovato");

			

			return data;

		}

/*	METODO GETITERATOR:
		EFFECTS: verifica username e password e, se il match corrisponde, restituisce l'iteratore
		MODIFIES: -
		THROWS:	IllegalArgumentException se Owner == null o passw == null 
				UserNotFoundException se non esiste corrispondenza tra username e password forniti
*/
		public Iterator<E> getIterator(String Owner, String passw)

				throws IllegalArgumentException,UserNotFoundException {

			

			if(!this.isValidValue(Owner,passw))

				throw 

					new IllegalArgumentException("Username o password non validi! "

							+ "La lunghezza dello username e della password deve essere "

							+ "compesa tra 6 e 16 ");

			

			Utente<E> tmp = this.userExists(Owner, passw);

			

			if(tmp == null)

				throw 

					new UserNotFoundException("Username o password errati !");

			

			List<E> list = tmp.getList();

			

			return 

					Collections.unmodifiableList(list).iterator();

		}

/*	METODO GETSIZE:
		EFFECTS: verifica username e password e, se il match corrisponde, restituisce il numeri di dati dell'utente
		MODIFIES: -
		THROWS:	IllegalArgumentException se Owner == null o passw == null 
				UserNotFoundException se non esiste corrispondenza tra username e password forniti
*/

		public int getSize(String Owner, String passw) 

				throws IllegalArgumentException,UserNotFoundException{

			

			if(!this.isValidValue(Owner,passw))

				throw 

					new IllegalArgumentException("Username o password non validi! "

							+ "La lunghezza dello username e della password deve essere "

							+ "compesa tra 6 e 16 ");

			

			Utente<E> tmp = this.userExists(Owner, passw);

			

			if(tmp == null)

				throw 

					new UserNotFoundException("Username o password errati !");

			

			return 

					tmp.ListSize();

		}

/*	METODO VALID:
		EFFECTS: verifica la correttezza della dimensione di username e password 
		MODIFIES: -
		THROWS:	-
*/


		private boolean isValidValue(String Id , String passw) {

			

			return 

				!(Id == null || passw == null || Id.length() < 6 || Id.length() > 16 

					|| passw.length() < 6 || passw.length() > 16);

		}

/*	METODO GETSIZE:
		EFFECTS: verifica username e password e, se il match corrisponde, restituisce il numero di oggetti
				 identici a quello passato per parametro nella collezione
		MODIFIES: -
		THROWS:	IllegalArgumentException se Owner == null o passw == null o l'oggetto data == null
				UserNotFoundException se non esiste corrispondenza tra username e password forniti
				DataMissingException se non è possibile trovare nei dati dell'utente l'oggetto
*/

		public int numberOf(String Owner, String passw, E data)

				throws IllegalArgumentException,UserNotFoundException{

			

			if(!this.isValidValue(Owner,passw))

				throw

					new IllegalArgumentException("Username o password non validi! "

							+ "La lunghezza dello username e della password deve essere "

							+ "compesa tra 6 e 16 ");

			

			if(data == null)

				throw

					new IllegalArgumentException("Oggetto non valido!");

		

			Utente<E> obj = userExists(Owner, passw);

			

			if(obj == null) 

				throw 

					new UserNotFoundException("Utente non presente nella lista!");

			

			int num = 0;

			

			for(E usr : obj.getList())

				if(usr.equals(data))

					num++;

			

			return num;

		}

/*	METODO PUT:
		EFFECTS: verifica username e password e, se il match corrisponde,inserisce il dato tra i dati utente
		MODIFIES: this.data
		THROWS:	IllegalArgumentException se Owner == null o passw == null o l'oggetto data == null
	 			UserNotFoundException se non esiste corrispondenza tra username e password forniti
*/

		public boolean put(String Owner, String passw, E data) 

				throws IllegalArgumentException,UserNotFoundException {

			

			if(!this.isValidValue(Owner,passw))

				throw 

					new IllegalArgumentException("Username o password non validi!"

							+ " La lunghezza dello username e della password deve essere"

							+ " compesa tra 6 e 16 ");

			

			if(data == null)

				throw 

					new IllegalArgumentException("Oggetto non valido!");

			

			Utente<E> tmp = this.userExists(Owner, passw);

			

			if(tmp == null)

				throw 

					new UserNotFoundException("Username o password errati !");

			

			return 

					tmp.addToList(data);

		}

/*	METODO REMOVE:
		EFFECTS: verifica username e password e, se il match corrisponde,rimuove l'utente con tutti i suoi dati
		MODIFIES: this
		THROWS:	IllegalArgumentException se Owner == null o passw == null o l'oggetto data == null
				UserNotFoundException se non esiste corrispondenza tra username e password forniti
				DataMissingException se non è possibile trovare nei dati dell'utente l'oggetto
*/

		public E remove(String Owner, String passw, E data) 

				throws IllegalArgumentException,UserNotFoundException,DataMissingException {

			

			if(!this.isValidValue(Owner,passw))

				throw 

					new IllegalArgumentException("Username o password non validi! "

							+ "La lunghezza dello username e della password deve essere "

							+ "compesa tra 6 e 16 ");

			

			if(data == null)

				throw 

					new IllegalArgumentException("Oggetto non valido!");

			

			Utente<E> tmp = this.userExists(Owner, passw);

			

			if(tmp == null)

				throw 

					new UserNotFoundException("Username o password errati !");

			

			boolean found = tmp.IsIn(data);

			

			if(!found)

				throw

					new DataMissingException("Oggetto non trovato");

			

			tmp.deleteElement(data);

			

			if(!tmp.deleteElement(data))

				return null;

				

			return data;

		}

/*	METODO SHARE:
		EFFECTS: verifica gli username e le password e, se il match corrisponde, condivide il dato tra i due utenti
		MODIFIES: -
		THROWS:	IllegalArgumentException se Owner == null o passw == null o l'oggetto data == null
				UserNotFoundException se non esiste corrispondenza tra username e password forniti
				DataMissingException se non è possibile trovare nei dati dell'utente l'oggetto
*/

		public void share(String Owner, String passw, String Other, E data) 

				throws IllegalArgumentException,UserNotFoundException,DataMissingException{

			

			if(!this.isValidValue(Owner,passw))

				throw 

					new IllegalArgumentException("Username o password non validi! "

							+ "La lunghezza dello username e della password deve essere "

							+ "compesa tra 6 e 16 ");

			

			if(data == null)

				throw 

					new IllegalArgumentException("Oggetto non valido!");

			

			Utente<E> tmp = this.userExists(Owner, passw);

			

			if(tmp == null)

				throw 

					new UserNotFoundException("Username o password errati !");

			

			Utente<E> sharedUser = this.userExists(Other);

			

			if(sharedUser == null)

				throw 

					new UserNotFoundException("Utente condiviso non presente !");

			

			boolean found = tmp.IsIn(data);

			

			if(!found)

				if(!tmp.addToList(data))

					throw 

						new DataMissingException("Oggetto non presente ed è impossibile aggiungerlo!");

			

			sharedUser.addToList(data);

			

		}

/*
		METODO USEREXISTS
		EFFECTS: Controlla se esiste un utente con l'id inserito 
	 	MODIFIES: -
	 	THROWS: -

*/

		public Utente<E> userExists(String Id) {

			for(Utente<E> tmp : Utente)

				if(tmp.getId() == Id)

					return tmp;

			return null;

		}

/*

		EFFECTS: Controlla se esiste un utente con id e password uguali a quelli passati per parametro
	 	MODIFIES: -
	 	THROWS: -

*/
		private Utente<E> userExists(String Id,String passw) {

			

			for(Utente<E> tmp : Utente)

				if(tmp.getId() == Id && tmp.getPassword() == passw)

					return tmp;

			

			return null;

		}



	}
