package progetto;
//OVERVIEW: Implementazione tramite l'uso di Vector dell'interfaccia SecurDataContainer

/* 
	IR = id != null 
		 && password != null
 		 && list != null

 	AF = (id, password, list) dove:
		ID è di tipo string
		PASSWORD è di tipo string
 		LIST è implementata tramite arraylist

 */

import java.util.ArrayList;



public class Utente<E> {



	private String id;

	private String password;

	private ArrayList<E> list = new ArrayList<E>();

/*
	COSTRUTTORE:crea l'utente con relativi id e password passati come parametro
*/

	public Utente(String id,String pass){

		this.id = id;

		this.password = pass;

		list = new ArrayList<E>();

	}

/*	METODO ADDTOLIST: 
 	EFFECTS: aggiunge l'oggetto passato per parametro alla lista
	MODIFIES: list
*/
	public boolean addToList(E obj) {

		return 

				list.add(obj);

	}

/*	METODO DELETEELEMENT: 
 	EFFECTS: elimina dalla lista l'oggetto passato per parametro
	MODIFIES: list
*/
	public boolean deleteElement(E obj) {

		boolean deleted = false;

		for(int i = 0; i < list.size() ; i++)

			if(list.get(i).equals(obj)) {

				list.remove(obj);

				deleted = true;

			}

		return deleted;

	}

/*	METODO GETID: 
 	EFFECTS: restituisce l'id dell'utente a cui è applicato il metodo
	MODIFIES: -
*/

	public String getId() {

		return id;

	}

/*	METODO LISTSIZE: 
 	EFFECTS: restituisce la lunghezza della lista a cui è applicato il metodo
	MODIFIES: -
*/

	public int ListSize() {

		return 

				list.size();

	}

/*	METODO GETPASSWORD: 
 	EFFECTS: restituisce la password dell'utente a cui è applicatoil metodo
	MODIFIES: -
*/

	public String getPassword() {

		return password;

	}

/*	METODO GETLIST: 
 	EFFECTS: restituisce la lista per intero
	MODIFIES: -
*/

	public ArrayList<E> getList() {

		return list;

	}

/*	METODO ISIN: 
 	EFFECTS: verifica l'esistenza dell'oggetto nella collezione
	MODIFIES: -
*/
	public boolean IsIn(E obj) {
		for(E elem : list)
			if(elem.equals(obj))return true;

		return false;

	}

/*	METODO SETID: 
 	EFFECTS: pone l'id dell'utente uguale a quella fornita
	MODIFIES: this.id
*/
	public void setId(String id) {

		this.id = id;

	}

/*	METODO SETPASSWORD: 
 	EFFECTS: pone la password dell'utente uguale a quella fornita
	MODIFIES: this.password
*/

	public void setPassword(String password) {
		this.password = password;
	}

}