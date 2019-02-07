package progetto;
import java.util.*;
public class Test {

	public static void main(String[] args) {
		MySecureDataContainer <String> tester= new MySecureDataContainer <String>();
		String dati="prova";
		try {
            tester.createUser("Giannino", "123456");
            System.out.println("Utente 'Giannino' creato con successo? : " + tester.userExists("Giannino") + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Inserito valore null : metodo creaUtente fallito\n");
        } catch (UserExistException e) {
            System.out.println("Utente già presente : metodo creaUtente fallito\n");
        }
		// Si testa il metodo getSize per gli utenti creati : se restituisce >=0 l'utente esiste e il valore indica il numero di elementi
				try {
					System.out.println("Numero elementi contenuti in Gianni : "+ tester.getSize("Gianni", "12345") +"\n");
				} catch (NullPointerException e) {
					System.out.println("Valore null : metodo getSize fallito\n");
				} catch (Exception e) {
					System.out.println("Password errata : metodo getSize fallito\n");
				}
				
				// Si testa il metodo put
				try {
					if(tester.put("Gianni", "135", dati))
						System.out.println("Elemento inserito; numero elementi contenuti in Gianni : "+ tester.getSize("Gianni", "135") +"\n");
					else
						System.out.println("Utente non presente : metodo put fallito\n");
				} catch (NullPointerException e) {
					System.out.println("Valore null : metodo put fallito\n");
				} catch (Exception e) {
					System.out.println("Password errata : metodo put fallito\n");
				}
				
				// Si testa il metodo get
				try {
					String prova = "Rossi";
					String tmp = tester.get("Gianni", "135", prova);
					if(tmp != null) {
						System.out.println("Dato richiesto = " + tmp);
					}
					else
						System.out.println("Dato non esistente\n");
				} catch (NullPointerException e) {
					System.out.println("Il valore inserito e' null : metodo get fallito\n");
				} catch (IllegalArgumentException e) {
					System.out.println("Utente non esistente : metodo get fallito\n");
				} catch (Exception e) {
					System.out.println("Password errata : metodo get fallito\n");
				}
				
				// Si testa la remove
				try {
					String prova2 = "Rossi";
					String tmp = tester.remove("Gianni", "135", prova2);
					if(tmp != null)
						System.out.println("Dato rimosso; numero elementi contenuti in Gianni : "+ tester.getSize("Gianni", "135") +"\n");
					else
						System.out.println("Dato non esistente\n");
				} catch (NullPointerException e) {
					System.out.println("Il valore inserito e' null : metodo remove fallito\n");
				} catch (IllegalArgumentException e) {
					System.out.println("Utente non esistente : metodo remove fallito\n");
				} catch (Exception e) {
					System.out.println("Password errata : metodo remove fallito\n");
				}
				
				// Si testa la copy, aggiungendo prima due dati alla collezione
				try {
					tester.put("Gianni", "135", "Bianco");
					tester.put("Gianni", "135", "Giallo");
					tester.put("Ugo", "531", "Arancio");
					System.out.println("Tre nuovi dati inseriti.\nNumero elementi contenuti in Gianni : "+ tester.getSize("Gianni", "135"));
				} catch (NullPointerException e1) {
					System.out.println("Inserito un valore null : metodo put fallito\n");
				} catch (Exception e1) {
					System.out.println("Password errata : metodo put fallito\n");
				}
				// Si testa getIterator
				try {
					String esempio = "Arancio";
					tester.copy("Gino", "531", esempio);
					System.out.println("Dato copiato.\nNumero elementi contenuti in utente 'Gianni' : "+ tester.getSize("Gianni", "135") );
					Iterator<String> iteratore= tester.getIterator("Gianni", "135");
					while(iteratore.hasNext()) {
						String tmp = iteratore.next();
						System.out.println(tmp);
					}
				} catch (NullPointerException e) {
					System.out.println("Inserito un valore null : metodo copy fallito\n");
				} catch (UserNotFoundException e) {
					System.out.println("Utente non esistente : metodo copy fallito\n");
				} catch (DataMissingException e) {
					System.out.println("Dato non esistente : : metodo copy fallito\n");
				} catch (Exception e) {
					System.out.println("Password errata : : metodo copy fallito\n");
				}
				
				// Si testa la share: prima di questa si inseriscono due nuovi utenti con nessun elemento associato
				try {
					tester.createUser("Francesca", "124");
					System.out.println("'Francesca' è stato creato? : " + tester.userExists("Francesca"));
					System.out.println("Numero elementi contenuti in Francesca : "+ tester.getSize("Francesca", "124"));
					tester.createUser("Giorgio", "125");
					System.out.println("'Giorgio' è stato creato? : " + tester.userExists("Giorgio"));
					System.out.println("Numero elementi contenuti in Giorgio : "+ tester.getSize("Giorgio", "125"));
				} catch (NullPointerException e) {
					System.out.println("Valore null : metodo createUser fallito\n");
				} catch (UserExistException e) {
					System.out.println("Utente già esistente : metodo createUser fallito\n");
				} catch (Exception e) {
					System.out.println("Password errata : metodo getSize fallito\n");
				}
				try {
					tester.share("Gianni", "135", "Francesca", "Rossi");
					System.out.println("Dato condiviso; numero Elementi contenuti in Francesca : "+ tester.getSize("Francesca", "124"));
				} catch (NullPointerException e) {
					System.out.println("Inserito un valore null : metodo share fallito\n");
				} catch (IllegalArgumentException e) {
					System.out.println("Utente non esistente : metodo share fallito\n");
				} catch (Exception e) {
					System.out.println("Password errata : metodo share fallito\n");
				}
				
				System.out.println("\n");
				System.out.println("test classe e completato");
			
		
		
				MySecureDataContainerV2 <String> tester2= new MySecureDataContainerV2 <String>();
				try {
		            tester2.createUser("Giannino", "123456");
		            System.out.println("Utente 'Gianni' creato con successo? : " + tester2.userExists("Giannino") + "\n");
		        } catch (IllegalArgumentException e) {
		            System.out.println("Inserito valore null : metodo creaUtente fallito\n");
		        } catch (UserExistException e) {
		            System.out.println("Utente già presente : metodo creaUtente fallito\n");
		        }
				// Si testa il metodo getSize per gli utenti creati : se restituisce >=0 l'utente esiste e il valore indica il numero di elementi
						try {
							System.out.println("Numero elementi contenuti in Gianni : "+ tester2.getSize("Gianni", "12345") +"\n");
						} catch (NullPointerException e) {
							System.out.println("Valore null : metodo getSize fallito\n");
						} catch (Exception e) {
							System.out.println("Password errata : metodo getSize fallito\n");
						}
						
						// Si testa il metodo put
						try {
							if(tester2.put("Gianni", "135", dati))
								System.out.println("Elemento inserito; numero elementi contenuti in Gianni : "+ tester2.getSize("Gianni", "135") +"\n");
							else
								System.out.println("Utente non presente : metodo put fallito\n");
						} catch (NullPointerException e) {
							System.out.println("Valore null : metodo put fallito\n");
						} catch (Exception e) {
							System.out.println("Password errata : metodo put fallito\n");
						}
						
						// Si testa il metodo get
						try {
							String prova = "Rossi";
							String tmp = tester2.get("Gianni", "135", prova);
							if(tmp != null) {
								System.out.println("Dato richiesto = " + tmp);
							}
							else
								System.out.println("Dato non esistente\n");
						} catch (NullPointerException e) {
							System.out.println("Il valore inserito e' null : metodo get fallito\n");
						} catch (IllegalArgumentException e) {
							System.out.println("Utente non esistente : metodo get fallito\n");
						} catch (Exception e) {
							System.out.println("Password errata : metodo get fallito\n");
						}
						
						// Si testa la remove
						try {
							String prova2 = "Rossi";
							String tmp = tester2.remove("Gianni", "135", prova2);
							if(tmp != null)
								System.out.println("Dato rimosso; numero elementi contenuti in Gianni : "+ tester2.getSize("Gianni", "135") +"\n");
							else
								System.out.println("Dato non esistente\n");
						} catch (NullPointerException e) {
							System.out.println("Il valore inserito e' null : metodo remove fallito\n");
						} catch (IllegalArgumentException e) {
							System.out.println("Utente non esistente : metodo remove fallito\n");
						} catch (Exception e) {
							System.out.println("Password errata : metodo remove fallito\n");
						}
						
						// Si testa la copy, aggiungendo prima due dati alla collezione
						try {
							tester2.put("Gianni", "135", "Bianco");
							tester2.put("Gianni", "135", "Giallo");
							tester2.put("Ugo", "531", "Arancio");
							System.out.println("Tre nuovi dati inseriti.\nNumero elementi contenuti in Gianni : "+ tester2.getSize("Gianni", "135"));
						} catch (NullPointerException e1) {
							System.out.println("Inserito un valore null : metodo put fallito\n");
						} catch (Exception e1) {
							System.out.println("Password errata : metodo put fallito\n");
						}
						// Si testa getIterator
						try {
							String esempio = "Arancio";
							tester2.copy("Gino", "531", esempio);
							System.out.println("Dato copiato.\nNumero elementi contenuti in utente 'Gianni' : "+ tester2.getSize("Gianni", "135") );
							Iterator<String> iteratore= tester2.getIterator("Gianni", "135");
							while(iteratore.hasNext()) {
								String tmp = iteratore.next();
								System.out.println(tmp);
							}
						} catch (NullPointerException e) {
							System.out.println("Inserito un valore null : metodo copy fallito\n");
						} catch (UserNotFoundException e) {
							System.out.println("Utente non esistente : metodo copy fallito\n");
						} catch (DataMissingException e) {
							System.out.println("Dato non esistente : : metodo copy fallito\n");
						} catch (Exception e) {
							System.out.println("Password errata : : metodo copy fallito\n");
						}
						
						// Si testa la share: prima di questa si inseriscono due nuovi utenti con nessun elemento associato
						try {
							tester.createUser("Francesca", "124");
							System.out.println("'Francesca' è stato creato? : " + tester.userExists("Francesca"));
							System.out.println("Numero elementi contenuti in Francesca : "+ tester.getSize("Francesca", "124"));
							tester.createUser("Giorgio", "125");
							System.out.println("'Giorgio' è stato creato? : " + tester.userExists("Giorgio"));
							System.out.println("Numero elementi contenuti in Giorgio : "+ tester.getSize("Giorgio", "125"));
						} catch (NullPointerException e) {
							System.out.println("Valore null : metodo createUser fallito\n");
						} catch (UserExistException e) {
							System.out.println("Utente già esistente : metodo createUser fallito\n");
						} catch (Exception e) {
							System.out.println("Password errata : metodo getSize fallito\n");
						}
						try {
							tester.share("Gianni", "135", "Francesca", "Rossi");
							System.out.println("Dato condiviso; numero Elementi contenuti in Francesca : "+ tester.getSize("Francesca", "124"));
						} catch (NullPointerException e) {
							System.out.println("Inserito un valore null : metodo share fallito\n");
						} catch (IllegalArgumentException e) {
							System.out.println("Utente non esistente : metodo share fallito\n");
						} catch (Exception e) {
							System.out.println("Password errata : metodo share fallito\n");
						}
						
						System.out.println("\n");
						System.out.println("test classe e completato");
					
		
	
	}

}
