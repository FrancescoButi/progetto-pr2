package progetto;

public class Test {

	public static void main(String[] args) {
		MySecureDataContainer <Utente> tester= new MySecureDataContainer <Utente>();
		try {
            tester.createUser("Pamnolo", "1hgjhvj35");
            System.out.println("Utente 'Pamnolo' creato con successo? : " + tester.userExists("Pamnolo") + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Inserito valore null : metodo creaUtente fallito\n");
        } catch (UserExistException e) {
            System.out.println("Utente già presente : metodo creaUtente fallito\n");
        }
		
		MySecureDataContainerV2 <Utente> tester2= new MySecureDataContainerV2 <Utente>();
		try {
            tester2.createUser("Panolo", "1hgjhvj35");
            System.out.println("Utente 'Panolo' creato con successo");
            if (tester2.userExists("Panolo") !=null ) {
            	System.out.println("utente inserito");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Inserito valore null : metodo creaUtente fallito\n");
        } catch (UserExistException e) {
            System.out.println("Utente già presente : metodo creaUtente fallito\n");
        }
	}

}
